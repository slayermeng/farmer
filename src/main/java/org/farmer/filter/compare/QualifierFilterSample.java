package org.farmer.filter.compare;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.farmer.filter.FilterBaseSample;

import java.io.IOException;

/**
 * User: mengxin
 * Date: 13-9-22
 * Time: 下午7:20
 */
public class QualifierFilterSample extends FilterBaseSample {

    public QualifierFilterSample(String tableName) throws IOException {
        table = new HTable(conf,tableName);
    }

    public static void main(String[] args) throws Exception{
        FilterBaseSample qf1 = new QualifierFilterSample("testtable");
        qf1.setFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("q2")));
        qf1.display();
    }
}
