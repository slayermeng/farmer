package org.farmer.filter;

/**
 * Created with IntelliJ IDEA.
 * User: gzy
 * Date: 13-11-18
 * Time: 上午9:13
 * To change this template use File | Settings | File Templates.
 */

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterBase;
import org.apache.hadoop.hbase.io.HbaseObjectWritable;
import org.apache.hadoop.io.Writable;
import org.farmer.parser.EndExpression;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;



/**
 * Implementation of {@link org.apache.hadoop.hbase.filter.Filter} that represents an ordered List of Filters
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class FilterTree implements Filter {

  //  private CCJSqlParserManager parserManager = new CCJSqlParserManager();
  //  private Select select;// = (Select)parserManager.parse(new StringReader("select * from test where a=1 and b=2 and c=3 and (d=4 or e=5 )and f=7"));
  //  private PlainSelect plainSelect;// = (PlainSelect) select.getSelectBody();
  //  private BinaryExpression Whereexp; //= (BinaryExpression)plainSelect.getWhere();
    private String mysql = null;
    private static final Configuration conf = HBaseConfiguration.create();
   // private List<Filter> filters = new ArrayList<Filter>();
  //  private Map<String,Expression> Columnm;// =  new TreeMap<String,EndExpression>();
 //   private Set<String> Columns = new TreeSet<String>();
    public static final Log LOG = LogFactory.getLog(FilterTree.class);
    private ExeSql exesql = null;
    /**
     * Default constructor, filters nothing. Required though for RPC
     * deserialization.
     */
    public FilterTree() {
        super();
    }

    public FilterTree(final String sql) {
        LOG.info("fffffff     FilterTree");
        initx(sql);
    }
    public void initx(final String sql){
        LOG.info("fffffff     initx");
        mysql = sql;
        exesql.init(sql);
       // Columnm =  exesql.getColumnmap();
    }



    @Override
    public ReturnCode filterKeyValue(KeyValue kv) {
       // boolean bin = false;
        LOG.info("fffffff     filterKeyValue r: "+new String( kv.getRow())+" f: "+new String( kv.getFamily())+
                  " q: "+new String( kv.getQualifier())+" v: "+new String( kv.getValue())+" k: "+ new String( kv.getKey()) );
       // String fq = new String( kv.getFamily())+"_"+new String( kv.getQualifier());
       // public boolean setColumnValue(String ColumnName,byte[] ColumnValue);
        boolean bin = exesql.setColumnValue(exesql.getColumnname(kv.getFamily(),kv.getQualifier()),kv.getValue());
        if(bin)
            return  ReturnCode.INCLUDE_AND_NEXT_COL;  //can be optimize
        else
            return  ReturnCode.NEXT_ROW;
    }

    @Override
    public void reset() {
        LOG.info("fffffff     reset");
        exesql.init(mysql);
    }

    @Override
    public boolean filterRowKey(byte[] rowKey, int offset, int length) {
        boolean rr= false;
        LOG.info("fffffff     filterRowKey " +offset+" "+length+" "+rr );
        String hexs = new String();
        for (int i = offset; i < offset+length; i++) {
            String hex = Integer.toHexString(rowKey[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
           }
            hexs+=' '+hex;
        }
        LOG.info("fffffff     filterRowKey: "  + hexs );
        return rr;
    }



    @Override
    public boolean filterRow() {
        LOG.info("fffffff     filterRow: ");
        boolean have = exesql.exe();
        if(!have)
            return false;
        else
            return true;
    }

    @Override
    public boolean filterAllRemaining() {
        boolean rr= false;
        LOG.info("fffffff     filterAllRemaining "+rr);
        return rr;
    }

    @Override
    public KeyValue transform(KeyValue v) {
        LOG.info("fffffff     transform "+v.toString());
        return v;
    }

    @Override
    public void filterRow(List<KeyValue> kvs) {
        LOG.info("fffffff     filterRow");

    }

    @Override
    public boolean hasFilterRow() {
        boolean rr= true;
        LOG.info("fffffff     hasFilterRow "+rr);
        return rr;
    }




    public void readFields(final DataInput in) throws IOException {
        LOG.info("fffffff     readFields");
        initx((String) HbaseObjectWritable.readObject(in, conf));
    }

    public void write(final DataOutput out) throws IOException {
        LOG.info("fffffff     write");
        HbaseObjectWritable.writeObject(out,mysql,String.class,conf);
    }


    @Override
    public KeyValue getNextKeyHint(KeyValue currentKV) {
        KeyValue keyHint = null;
        return keyHint;
    }

    public boolean isFamilyEssential(byte[] name) {
        return false;
    }

    @Override
    public String toString() {
        return mysql;
    }


}
