package org.farmer.filter;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.filter.FilterBase;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-11-12
 * Time: 下午2:08
 * To change this template use File | Settings | File Templates.
 */
public class MultiColumnValueFilter extends FilterBase {
    private final BinaryExpression expression;

    public MultiColumnValueFilter(final BinaryExpression expression){
        this.expression = expression;
    }

    public ReturnCode filterKeyValue(KeyValue kv) {
        postOrder(expression);
        return ReturnCode.INCLUDE;
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

    public void readFields(DataInput dataInput) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void write(DataOutput dataOutput) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
