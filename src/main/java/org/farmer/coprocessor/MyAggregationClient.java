package org.farmer.coprocessor;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;

/**
 * User: mengxin
 * Date: 13-10-12
 * Time: 下午4:54
 */
public class MyAggregationClient {
    private static final byte[] TABLE_NAME = Bytes.toBytes("testtable");

    private static final byte[] CF = Bytes.toBytes("f1");

    public static void main(String[] args) throws Throwable{
        Configuration conf  = HBaseConfiguration.create();
        AggregationClient aggregationClient = new AggregationClient(conf);
        Scan scan = new Scan();
        scan.addFamily(CF);
        long rowCount = aggregationClient.rowCount(TABLE_NAME,null,scan);
        System.out.println("total row:"+rowCount);
    }
}
