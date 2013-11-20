package org.farmer.transfer.expression;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.expression.Expression;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;

/**
 * User: mengxin
 * Date: 13-11-19
 * Time: 下午3:09
 */
public class WhereClause extends FilterBase{
    public static final Log LOG = LogFactory.getLog(WhereClause.class);

    private Map<String,Filter> filters = new LinkedHashMap<String,Filter>();

    private Map<String,Boolean> expressions = new HashMap<String,Boolean>();

    public void readFields(DataInput dataInput) throws IOException {
        Bytes.readByteArray(dataInput);
    }

    public void write(DataOutput dataOutput) throws IOException {
        Bytes.writeByteArray(dataOutput,Bytes.toBytes(this.sql));
    }

    public ReturnCode filterKeyValue(KeyValue keyValue) {
        ReturnCode rc = null;
        for(Map.Entry<String,Filter> entry:filters.entrySet()){
            Filter filter = entry.getValue();
            rc = filter.filterKeyValue(keyValue);
            if(rc==ReturnCode.INCLUDE){
                expressions.put(entry.getKey(),Boolean.TRUE);
            }else if(rc==ReturnCode.NEXT_ROW){
                expressions.put(entry.getKey(),Boolean.FALSE);
            }
        }
        return rc;
    }

    public void addFilter(String filterName,Filter filter) {
        this.filters.put(filterName,filter);
    }

    /**
     * 是否返回结果
     * @return
     */
    public boolean filterRow() {
        this.parser();
        return this.expressionResult;
    }

    private CCJSqlParserManager parserManager = new CCJSqlParserManager();

    private final String sql;

    private boolean expressionResult;

    public WhereClause(Map<String,Filter> filters,String sql){
        this.filters = filters;
        this.sql = sql;
    }

    public void parser(){
        Expression expression=null;
        try{
            Select select = (Select)parserManager.parse(new StringReader(sql));
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
            expression = plainSelect.getWhere();
        }catch(JSQLParserException e){
            e.printStackTrace();
        }
        if(expression!=null&&expression instanceof BinaryExpression){
            recursive((BinaryExpression)expression);//递归
        }
    }

    private void recursive(BinaryExpression expression){
        //后续遍历
        if(expression.getLeftExpression() instanceof BinaryExpression){
            recursive((BinaryExpression)expression.getLeftExpression());
        }
        if (expression.getRightExpression() instanceof BinaryExpression){
            recursive((BinaryExpression)expression.getRightExpression());
        }
        execExpression(expression);
    }

    private void execExpression(BinaryExpression expression){
        System.out.println("当前表达式:"+expression.toString());
        if(expression.getLeftExpression() instanceof Column){
            // 原子表达式转化为filter
           if(expression.getStringExpression().equals("=")){
               Filter filter = new SingleColumnValueFilter(Bytes.toBytes(expression.getLeftExpression().toString()),
                       Bytes.toBytes(((BinaryExpression)expression).getLeftExpression().toString()),
                       CompareFilter.CompareOp.EQUAL,
                       new BinaryComparator(Bytes.toBytes(expression.getRightExpression().toString())));
               if(expressions.get(expression.toString())==Boolean.TRUE){
                   expression.setNot();//符合条件
               }
           }else if(expression.getStringExpression().equals(">=")){
               Filter filter = new SingleColumnValueFilter(Bytes.toBytes(expression.getLeftExpression().toString()),
                       Bytes.toBytes(((BinaryExpression)expression).getLeftExpression().toString()),
                       CompareFilter.CompareOp.GREATER_OR_EQUAL,
                       new BinaryComparator(Bytes.toBytes(expression.getRightExpression().toString())));
           }else if(expression.getStringExpression().equals("<=")){
               Filter filter = new SingleColumnValueFilter(Bytes.toBytes(expression.getLeftExpression().toString()),
                       Bytes.toBytes(((BinaryExpression)expression).getLeftExpression().toString()),
                       CompareFilter.CompareOp.LESS_OR_EQUAL,
                       new BinaryComparator(Bytes.toBytes(expression.getRightExpression().toString())));
           }else if(expression.getStringExpression().equals("<")){
               Filter filter = new SingleColumnValueFilter(Bytes.toBytes(expression.getLeftExpression().toString()),
                       Bytes.toBytes(((BinaryExpression)expression).getLeftExpression().toString()),
                       CompareFilter.CompareOp.LESS,
                       new BinaryComparator(Bytes.toBytes(expression.getRightExpression().toString())));
           }else if(expression.getStringExpression().equals(">")){
               Filter filter = new SingleColumnValueFilter(Bytes.toBytes(expression.getLeftExpression().toString()),
                       Bytes.toBytes(((BinaryExpression)expression).getLeftExpression().toString()),
                       CompareFilter.CompareOp.GREATER,
                       new BinaryComparator(Bytes.toBytes(expression.getRightExpression().toString())));
           }else if(expression.getStringExpression().equals("<>")){
               Filter filter = new SingleColumnValueFilter(Bytes.toBytes(expression.getLeftExpression().toString()),
                       Bytes.toBytes(((BinaryExpression)expression).getLeftExpression().toString()),
                       CompareFilter.CompareOp.NOT_EQUAL,
                       new BinaryComparator(Bytes.toBytes(expression.getRightExpression().toString())));
           }
        }

        //And、OR表达式
        boolean result = false;
        if(expression instanceof AndExpression){
            boolean left = ((BinaryExpression)expression.getLeftExpression()).isNot();
            boolean right = ((BinaryExpression)expression.getRightExpression()).isNot();
            result = left & right;
            expression.setNot();
        }else if(expression instanceof OrExpression){
            boolean left = ((BinaryExpression)expression.getLeftExpression()).isNot();
            boolean right = ((BinaryExpression)expression.getRightExpression()).isNot();
            result = left | right;
            expression.setNot();
        }
        expressionResult = result;
    }

    public static void main(String[] args){
//        WhereClause where = new WhereClause("select * from test where a=1 and b=2 or c=3 and d=4");
//        where.parser();
    }
}
