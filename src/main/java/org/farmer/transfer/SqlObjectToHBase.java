package org.farmer.transfer;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.WithItem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-16
 * Time: 上午10:41
 * To change this template use File | Settings | File Templates.
 */
public class SqlObjectToHBase {
    CCJSqlParserManager parserManager = new CCJSqlParserManager();

    Configuration config = HBaseConfiguration.create();


    public void scan(){
        String statement = "select q1 from testtable where q1='mengxin' and q2=30";
        try{
            Select select = (Select) parserManager.parse(new StringReader(statement));
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
            List<WithItem> item = select.getWithItemsList();
            System.out.println(plainSelect.getFromItem());
            construct(plainSelect);
        }catch(JSQLParserException e){
            System.out.println(e);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void construct(PlainSelect select) throws IOException{
        HTable table = new HTable(config,select.getFromItem().toString());
        Expression expression = select.getWhere();
        String cond1="";
        String cond2="";
        if(expression instanceof AndExpression){
            AndExpression ex = (AndExpression)expression;
            cond1 = ex.getLeftExpression().toString();
            cond2 = ex.getRightExpression().toString();
            System.out.println(ex.getRightExpression());
        }
        select.getSelectItems();

        List<Filter> filters = new ArrayList<Filter>();

        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("f1"),
                Bytes.toBytes(cond1.split("=")[0]),
                CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes(cond1.split("=")[1])));
        filter.setFilterIfMissing(true);
        filters.add(filter);

        SingleColumnValueFilter filter1 = new SingleColumnValueFilter(Bytes.toBytes("f2"),
                Bytes.toBytes(cond2.split("=")[0]),
                CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes(cond2.split("=")[1])));
        filter1.setFilterIfMissing(true);
        filters.add(filter1);

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE,filters);

        Scan scan = new Scan();
        scan.setFilter(filterList);
        ResultScanner scanner = table.getScanner(scan);
        for(Result result:scanner){
            for(KeyValue kv:result.raw()){
                System.out.println("KV:"+kv+",Value:"+Bytes.toString(kv.getValue()));
            }
        }
    }

    public static void main(String[] args) throws Exception{
        SqlObjectToHBase obj = new SqlObjectToHBase();
        obj.scan();
    }
}
