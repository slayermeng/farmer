package org.farmer.filter;

/**
 * Created with IntelliJ IDEA.
 * User: gzy
 * Date: 13-11-18
 * Time: 上午9:13
 * To change this template use File | Settings | File Templates.
 */

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
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
import org.apache.hadoop.hbase.io.HbaseObjectWritable;
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
public class FilterTreeDemo implements Filter {

    private CCJSqlParserManager parserManager = new CCJSqlParserManager();
    private Select select;// = (Select)parserManager.parse(new StringReader("select * from test where a=1 and b=2 and c=3 and (d=4 or e=5 )and f=7"));
    private PlainSelect plainSelect;// = (PlainSelect) select.getSelectBody();
    private BinaryExpression Whereexp; //= (BinaryExpression)plainSelect.getWhere();
    private String mysql = null;
    private static final Configuration conf = HBaseConfiguration.create();
   // private List<Filter> filters = new ArrayList<Filter>();
    private Map<String,EndExpression> Columnm =  new TreeMap<String,EndExpression>();
    private Set<String> Columns = new TreeSet<String>();
    public static final Log LOG = LogFactory.getLog(FilterTreeDemo.class);

    /**
     * Default constructor, filters nothing. Required though for RPC
     * deserialization.
     */
    public FilterTreeDemo() {
        super();
    }

    public FilterTreeDemo(final String sql) {
        LOG.info("fffffff     FilterTree");
        initx(sql);
    }
    public void initx(final String sql){
        LOG.info("fffffff     initx");
        this.mysql = sql;
        Columnm.clear();
        try {
            select = (Select)parserManager.parse(new StringReader(mysql));
            plainSelect = (PlainSelect) select.getSelectBody();
            Whereexp = (BinaryExpression)plainSelect.getWhere();
            Traversalwhere(Whereexp);
        } catch (JSQLParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public  void Traversalwhere(Expression expression){
        if(expression == null)
            return ;
        if(expression instanceof BinaryExpression) {
            if(((BinaryExpression)expression).getLeftExpression() != null){
                Traversalwhere(((BinaryExpression) expression).getLeftExpression());
            }
            if(((BinaryExpression)expression).getRightExpression() != null){
                Traversalwhere(((BinaryExpression) expression).getRightExpression());
            }

        }else if(expression instanceof Column){
            //     LOG.info("xxxxxxx     Column:"+expression);
            Columnm.put(((Column) expression).getColumnName(), null);
        }
        return ;
    }

    public  EndExpression getColumnvalue(Column expression){
        if(expression == null)
            return null;
        return Columnm.get(expression.getColumnName());
    }

    public  EndExpression exeNode(Expression expression){
        LOG.info("xxxxxxx     exeNode:"+expression.toString()+" class:"+expression.getClass());
        Long ll=null,rl=null;

        if(expression == null)
            return null;
        if(expression instanceof BinaryExpression) {
            LOG.info("xxxxxxx     exeNode if:"+expression.toString());

            Expression le,re;
            le = ((BinaryExpression)expression).getLeftExpression();
            re = ((BinaryExpression)expression).getRightExpression();
            if( null == le){
                ll =null;
            }else if( le instanceof Column){
                ll = Long.parseLong(new String(getColumnvalue((Column) le).getValue()));
            }else if( le instanceof LongValue){
                ll = ((LongValue) le).getValue();
            }else if( le instanceof EndExpression){
                ll =  Long.parseLong(  new String(   ((EndExpression) le).getValue()  )  );
            }
            if( null == re){
                ll =null;
            }else if( re instanceof Column){
                rl = Long.parseLong(new String(getColumnvalue((Column) re).getValue()));
            }else if( re instanceof LongValue){
                rl = ((LongValue) re).getValue();
            }else if( re instanceof EndExpression){
                rl =  Long.parseLong(new String(   ((EndExpression) re).getValue()  ));
            }
            if((null==ll)||(null==rl)){
                LOG.info("xxxxxxx     exeNode no support !!!!!!!!! "+le.getClass()+" "+re.getClass());
                return null;
            }

            Integer r;
            String es = ((BinaryExpression) expression).getStringExpression();
            if(es.equals("<=")){
                r =  ll <= rl ? 1 : 0;
            }else if(es.equals("<")){
                r =  ll < rl ? 1 : 0;
            }else if(es.equals("<>")){
                r =  ll != rl ? 1 : 0;
            }else if(es.equals("=")){
                r =  ll == rl ? 1 : 0;
            }else if(es.equals(">")){
                r =  ll > rl ? 1 : 0;
            }else if(es.equals(">=")){
                r =  ll >= rl ? 1 : 0;
            }else if(es.equals("AND")){
               if((ll>0)&&(rl>0))
                   r = 1;
                else
                   r = 0;
            }else if(es.equals("OR")){
                if((ll>0)||(rl>0))
                    r = 1;
                else
                    r = 0;
            }else{
                //  throw new RuntimeException("Unknown Compare op " + compareOp.name());
                LOG.info("xxxxxxx     exeNode no support !!!!!!!!! " + es);
                return null;
            }

            EndExpression ee = new EndExpression(r.toString().getBytes(),"endbool");
            LOG.info("xxxxxxx     exeNode result: "+ee.toString());
            return ee;
        }else{
            LOG.info("xxxxxxx     exeNode else:"+expression.toString()+" class:"+expression.getClass());
        }
        return null;
    }
    public  EndExpression exeexpression(Expression expression){
        LOG.info("xxxxxxx     exewhere:"+expression+" class:"+expression.getClass());
        Expression LeftExpression,RightExpression;
        if(expression == null)
            return null;
        if(expression instanceof BinaryExpression) {
       //     LOG.info("xxxxxxx     xl:"+((BinaryExpression)expression).getLeftExpression()+"  r: "+((BinaryExpression)expression).getLeftExpression().getClass());
      //      LOG.info("xxxxxxx     xc:"+((BinaryExpression)expression).getStringExpression()+"  c: "+((BinaryExpression)expression).getStringExpression().getClass() );
       //     LOG.info("xxxxxxx     xr:"+((BinaryExpression)expression).getRightExpression()+"  l: "+((BinaryExpression)expression).getRightExpression().getClass());
            if(((BinaryExpression)expression).getLeftExpression() instanceof BinaryExpression){
                LeftExpression = exeexpression(((BinaryExpression) expression).getLeftExpression());
                ((BinaryExpression)expression).setLeftExpression(LeftExpression);
            }
            if(((BinaryExpression)expression).getRightExpression() instanceof BinaryExpression){
                RightExpression = exeexpression(((BinaryExpression) expression).getRightExpression());
                ((BinaryExpression)expression).setRightExpression(RightExpression);
            }
            return exeNode(expression);
        }
        LOG.info("xxxxxxx     exewhere end:"+expression.toString() );
        return exeNode(expression);
    }

    @Override
    public ReturnCode filterKeyValue(KeyValue kv) {
        boolean bin = false;
        LOG.info("fffffff     filterKeyValue r: "+new String( kv.getRow())+" f: "+new String( kv.getFamily())+
                  " q: "+new String( kv.getQualifier())+" v: "+new String( kv.getValue())+" k: "+ new String( kv.getKey()) );
        String fq = new String( kv.getFamily())+"_"+new String( kv.getQualifier());
       if(Columnm.containsKey(fq)){
           Columnm.put(fq , new EndExpression( kv.getValue(),null));
          // bin = true;
       }
        LOG.info("fffffff     Columnm: "+Columnm.toString());

        for (String key : Columnm.keySet()) {
            LOG.info("fffffff     filterKeyValue  it: k:"+key+" v:"+Columnm.get(key));
            if(null == Columnm.get(key)){
                LOG.info("fffffff     filterKeyValue  ReturnCode: "+bin);
                return  ReturnCode.INCLUDE_AND_NEXT_COL ;
              /*
                if(bin)
                    return ReturnCode.INCLUDE_AND_NEXT_COL;
                else
                    return  ReturnCode.NEXT_COL ;
              */
            }
        }
        LOG.info("fffffff     filterKeyValue  ReturnCode.NEXT_ROW");
        return  ReturnCode.NEXT_ROW;
    }

    @Override
    public void reset() {
        LOG.info("fffffff     reset");
        initx(mysql);
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
        EndExpression ee = exeexpression(Whereexp);
        long r =  Long.parseLong(new String(   ((EndExpression) ee).getValue()  ));
        LOG.info("fffffff     filterRow: "+r+" ee: "+ee);
        if(r !=0)
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
