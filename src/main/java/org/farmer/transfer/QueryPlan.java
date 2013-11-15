package org.farmer.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;
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
import org.farmer.filter.MultiColumnValueFilter;
import net.sf.jsqlparser.schema.Column;
import java.sql.SQLSyntaxErrorException;

/**
 * User: mengxin
 * Date: 13-11-4
 * Time: 上午10:46
 */
public class QueryPlan implements Plan {
    protected static final Configuration config = HBaseConfiguration.create();

    private PlainSelect select = null;

    private Scan scan = new Scan();

    private Map<Integer,Double> aggColumnResult = new HashMap<Integer,Double>();

    public QueryPlan(PlainSelect select){
        this.select = select;
    }

    public List<Object> plan(){
        return null;
    }

    /**
     * get tableName for query
     */
    protected String getTableName() throws SQLSyntaxErrorException{
        String tableName = select.getFromItem().toString();
        if(tableName==null){
            throw new SQLSyntaxErrorException();
        }
        return tableName;
    }

    /**
     * handle select item
     */
    private void handleSelectItem(){
        List<SelectItem> items = select.getSelectItems();//目标列集合
        for(int i=0;i<items.size();i++){
            SelectItem item = items.get(i);
            String targetColumn = item.toString();
            if(targetColumn.contains("max")||targetColumn.contains("min")||targetColumn.contains("avg")||
                    targetColumn.contains("count")||targetColumn.contains("sum")){
                AggregationOperator aggop = new AggregationOperator();
                try{
                    aggColumnResult.put(i, aggop.aggregate(targetColumn, getTableName(), scan));
                }catch(Throwable t){
                    t.printStackTrace();
                }
            }else{
                scan.addColumn(Bytes.toBytes("f1"), Bytes.toBytes(targetColumn));
            }
        }
    }

    /**
     * handle where condition
     */
    private void handelWhereItem(){
        BinaryExpression expression = (BinaryExpression)select.getWhere();
        postOrder(expression);
        Filter filter = new MultiColumnValueFilter(expression);
        scan.setFilter(filter);
    }

    public static void postOrder(BinaryExpression expression){
        if(expression != null){
            if((expression.getLeftExpression() instanceof BinaryExpression)&&(expression.getRightExpression() instanceof BinaryExpression)){
                postOrder((BinaryExpression)expression.getLeftExpression());
                postOrder((BinaryExpression)expression.getRightExpression());
            }
            printNode(expression);
        }
    }

    public static void printNode(Expression expression){
        System.out.println(expression.toString());
    }

    /**
     * Recurse expression
     * append filter
     * @param expression
     * @param filters
     */
    private void appendCondition(Expression expression,List<Filter> filters){
        //select * from name='mengxin' and age=30 or sex=1 and school='beijing';
        if(expression instanceof AndExpression){
            AndExpression andEx = (AndExpression)expression;
            Expression left = andEx.getLeftExpression();
            appendCondition(left,filters);
            Expression right = andEx.getRightExpression();
            appendCondition(right,filters);
        }else if(expression instanceof OrExpression){
            OrExpression orEx = (OrExpression)expression;
            Expression left = orEx.getLeftExpression();
            appendCondition(left,filters);
            Expression right = orEx.getRightExpression();
            appendCondition(right,filters);
        }else{
            appendFilter(expression,filters);
            return;
        }
    }

    private void appendFilter(Expression expression,List<Filter> filters){
        if(expression == null){
            return;
        }
        SingleColumnValueFilter filter = null;
        //运算符实现
        if(expression instanceof EqualsTo){//=
            EqualsTo eqExp = (EqualsTo)expression;
            filter = new SingleColumnValueFilter(Bytes.toBytes("f1"),
                    Bytes.toBytes(eqExp.getLeftExpression().toString()),
                    CompareFilter.CompareOp.EQUAL,
                    new BinaryComparator(Bytes.toBytes(eqExp.getRightExpression().toString())));
            filter.setFilterIfMissing(true);
        }else if(expression instanceof GreaterThan){//>
            GreaterThan greatExp = (GreaterThan)expression;
            filter = new SingleColumnValueFilter(Bytes.toBytes("f1"),
                    Bytes.toBytes(greatExp.getLeftExpression().toString()),
                    CompareFilter.CompareOp.GREATER,
                    new BinaryComparator(Bytes.toBytes(greatExp.getRightExpression().toString())));
            filter.setFilterIfMissing(true);
        }else if(expression instanceof GreaterThanEquals){//>=
            GreaterThanEquals greatEqExp = (GreaterThanEquals)expression;
            filter = new SingleColumnValueFilter(Bytes.toBytes("f1"),
                    Bytes.toBytes(greatEqExp.getLeftExpression().toString()),
                    CompareFilter.CompareOp.GREATER_OR_EQUAL,
                    new BinaryComparator(Bytes.toBytes(greatEqExp.getRightExpression().toString())));
            filter.setFilterIfMissing(true);
        }else if(expression instanceof MinorThan){//<
            MinorThan minExp = (MinorThan)expression;
            filter = new SingleColumnValueFilter(Bytes.toBytes("f1"),
                    Bytes.toBytes(minExp.getLeftExpression().toString()),
                    CompareFilter.CompareOp.LESS,
                    new BinaryComparator(Bytes.toBytes(minExp.getRightExpression().toString())));
            filter.setFilterIfMissing(true);
        }else if(expression instanceof MinorThanEquals){//<=
            MinorThanEquals minEqExp = (MinorThanEquals)expression;
            filter = new SingleColumnValueFilter(Bytes.toBytes("f1"),
                    Bytes.toBytes(minEqExp.getLeftExpression().toString()),
                    CompareFilter.CompareOp.LESS_OR_EQUAL,
                    new BinaryComparator(Bytes.toBytes(minEqExp.getRightExpression().toString())));
            filter.setFilterIfMissing(true);
        }else if(expression instanceof InExpression){//in
            InExpression inExp = (InExpression)expression;
            Column column = (Column)inExp.getLeftExpression();
            ItemsList value = inExp.getRightItemsList();
            if(value instanceof ExpressionList){
                ExpressionList exlist = (ExpressionList)value;
                List v = exlist.getExpressions();
                for(int i=0;i<v.size();i++){
                    LongValue lv = (LongValue)v.get(i);
                    filter = new SingleColumnValueFilter(Bytes.toBytes("f1"),
                            Bytes.toBytes(column.getColumnName()),
                            CompareFilter.CompareOp.EQUAL,
                            new BinaryComparator(Bytes.toBytes(lv.getValue())));
                    filter.setFilterIfMissing(true);
                }
            }
        }

        filters.add(filter);
    }

    /**
     * execute query plan
     * @param table
     * @throws IOException
     */
    private void execScan(HTable table) throws IOException{
        //结果处理
        ResultScanner scanner = table.getScanner(scan);
        for(Result result:scanner){
            for(KeyValue kv:result.raw()){
                System.out.println("KV:"+kv+",Value:"+Bytes.toString(kv.getValue()));
            }
        }
    }

    /**
     * merge result
     */
    private void merge(){

    }

    private Map buildScan() throws IOException{
        HTable table = null;
        try{
            table = new HTable(config,getTableName());
        }catch(SQLSyntaxErrorException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        handelWhereItem();//处理条件子句
        handleSelectItem();//处理目标列
        execScan(table);
        return null;
    }
}
