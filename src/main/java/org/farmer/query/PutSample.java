package org.farmer.query;

import java.io.IOException;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;


/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-3
 * Time: 下午6:40
 * To change this template use File | Settings | File Templates.
 */
public class PutSample {
    public static void main(String[] args) throws IOException{
        Configuration conf = HBaseConfiguration.create();
        HTable table = new HTable(conf,"testtable");
        Put put = new Put(Bytes.toBytes("row1"));
        put.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val1"));
        put.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual2"),Bytes.toBytes("val2"));
        table.put(put);

        //autoflush
        table.setAutoFlush(false);
        Put put1 = new Put(Bytes.toBytes("row1"));
        put1.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val1"));
        table.put(put1);

        Put put2 = new Put(Bytes.toBytes("row2"));
        put2.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val2"));
        table.put(put2);

        Put put3 = new Put(Bytes.toBytes("row3"));
        put3.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val3"));
        table.put(put1);

        Get get = new Get(Bytes.toBytes("row1"));
        Result res1 = table.get(get);
        System.out.println("Result:"+res1);

        table.flushCommits();

        Result res2 = table.get(get);
        System.out.println("Result:"+res2);
    }
}
