package org.farmer.transfer;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
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
import org.farmer.coprocessor.AggregationOperator;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

/**
 * User: mengxin
 * Date: 13-10-16
 * Time: 上午10:41
 */
public class SqlObjectToHBase {
    CCJSqlParserManager parserManager = new CCJSqlParserManager();

    Configuration config = HBaseConfiguration.create();


    public List scan(PlainSelect plainSelect){
        List result = new ArrayList();
        try{
            result = construct(plainSelect);
        }catch(IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public List construct(PlainSelect select) throws IOException{
        HTable table = new HTable(config,select.getFromItem().toString());
        Scan scan = new Scan();
        //item处理
        List<SelectItem> items = select.getSelectItems();
        List resultItem = new ArrayList();


        //where条件处理
        Expression expression = select.getWhere();
        String cond1="";
        String cond2="";
        if(expression instanceof AndExpression){
            AndExpression ex = (AndExpression)expression;
            cond1 = ex.getLeftExpression().toString();
            cond2 = ex.getRightExpression().toString();
        }


        List<Filter> filters = new ArrayList<Filter>();

        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("f1"),
                Bytes.toBytes(cond1.split("=")[0].trim()),
                CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes(cond1.split("=")[1].trim())));
        filter.setFilterIfMissing(true);
        filters.add(filter);

        SingleColumnValueFilter filter1 = new SingleColumnValueFilter(Bytes.toBytes("f2"),
                Bytes.toBytes(cond2.split("=")[0].trim()),
                CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes(cond2.split("=")[1].trim())));
        filter1.setFilterIfMissing(true);
        filters.add(filter1);
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL,filters);
        scan.setFilter(filterList);

        for(SelectItem item:items){
            String i = item.toString();
            if(i.contains("count")||i.contains("sum")||i.contains("max")||i.contains("min")||i.toString().contains("avg")){
                AggregationOperator aggop = new AggregationOperator();
                try{
                    resultItem.add(aggop.aggregate(i,select.getFromItem().toString(),scan));
                }catch(Throwable t){
                    t.printStackTrace();
                }
            }
        }

        System.out.println("agg结果:"+resultItem.size());

        //结果处理
        ResultScanner scanner = table.getScanner(scan);
        for(Result result:scanner){
            for(KeyValue kv:result.raw()){
                System.out.println("KV:"+kv+",Value:"+Bytes.toString(kv.getValue()));
            }
        }

        return resultItem;
    }
}
