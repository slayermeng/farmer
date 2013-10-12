package org.farmer.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.WritableByteArrayComparable;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * User: mengxin
 * Date: 13-10-12
 * Time: 下午1:53
 */
public abstract class FilterBaseSample {
    public static Configuration conf = HBaseConfiguration.create();

    public HTable table = null;

    public Scan scan = new Scan();

    public void scan(String family,String qualifier){
        scan.addColumn(Bytes.toBytes(family),Bytes.toBytes(qualifier));
    }

    public void setFilter(CompareFilter.CompareOp rowCompareOp,WritableByteArrayComparable comparator){
        Filter filter = new RowFilter(rowCompareOp,comparator);
        scan.setFilter(filter);
    }

    public void display() throws IOException {
        ResultScanner scanner = table.getScanner(scan);
        for(Result res:scanner){
            System.out.println(res);
        }
        scanner.close();
    }

}
