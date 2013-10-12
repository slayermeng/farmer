package org.farmer.filter.compare;

import java.io.IOException;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.farmer.filter.FilterBaseSample;

/**
 * User: mengxin
 * Date: 13-10-12
 * Time: 上午11:16
 */
public class RowFilterSample extends FilterBaseSample {
    public RowFilterSample(String tableName) throws IOException{
        table = new HTable(conf,tableName);
    }

    public static void main(String[] args) throws Exception{
        FilterBaseSample rf1 = new RowFilterSample("testtable");
        rf1.scan("f1","q1");
        rf1.setFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("rk1")));
        rf1.display();

        RowFilterSample rf2 = new RowFilterSample("testtable");
        rf2.scan("f1","q1");
        rf2.setFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator("r*2"));
        rf2.display();


    }
}
