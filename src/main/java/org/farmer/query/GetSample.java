package org.farmer.query;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-7
 * Time: 下午5:22
 * To change this template use File | Settings | File Templates.
 */
public class GetSample {
    public static void main(String[] args) throws Exception{
        Configuration conf = HBaseConfiguration.create();
        HTable table = new HTable(conf,"testtable");
        Get get = new Get(Bytes.toBytes("row1"));
        get.addColumn(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"));
        Result result = table.get(get);
        byte[] val = result.getValue(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"));
        System.out.println("Value:"+Bytes.toString(val));

    }
}
