package org.farmer.filter;

import net.sf.jsqlparser.expression.BinaryExpression;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.filter.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.expression.Expression;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-11-14
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public class MultiLogicFilterList extends FilterBase {

    private List<Expression> expressions = new ArrayList<Expression>();

    public void addExpressions(Expression expression) {
        this.expressions.add(expression);
    }

    @Override
    public void filterRow(List<KeyValue> kvs) {
        List<ReturnCode> resultList = new ArrayList<ReturnCode>();
        for(Expression expression:expressions){
            if(!(expression instanceof BinaryExpression)){
                Filter filter = new SingleColumnValueFilter(Bytes.toBytes("f1"),
                        Bytes.toBytes(((BinaryExpression)expression).getLeftExpression().toString()),
                        CompareFilter.CompareOp.EQUAL,
                        new BinaryComparator(Bytes.toBytes("1")));
                ReturnCode rt = filter.filterKeyValue(kvs.get(0));
                resultList.add(rt);//filter结果
            }
        }

    }

    public void readFields(DataInput dataInput) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void write(DataOutput dataOutput) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
