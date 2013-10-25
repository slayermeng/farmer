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
 * User: mengxin
 * Date: 13-10-3
 * Time: 下午6:40
 */
public class PutSample {
    private static Configuration conf = HBaseConfiguration.create();

    private HTable table = null;

    public PutSample(String tableName) throws IOException{
        table = new HTable(conf,tableName);
    }

    public void insert(String rowkey,String family,String qualifier,String value) throws IOException{
        insert(rowkey, family, qualifier, value,true);
    }

    public void insert(String rowkey,String family,String qualifier,String value,boolean autoFlush) throws IOException{
        table.setAutoFlush(autoFlush);
        Put put = new Put(Bytes.toBytes(rowkey));
        put.add(Bytes.toBytes(family),Bytes.toBytes(qualifier),Bytes.toBytes(value));
        table.put(put);
    }

    public void commit() throws IOException{
        table.flushCommits();
    }

    public static void main(String[] args) throws IOException{
        PutSample put = new PutSample("testtable");
        put.insert("rk1","f1","q1","mengxin");
        put.insert("rk1","f1","q2","30");
        put.insert("rk2","f1","q1","guanzy");
        put.insert("rk2","f1","q2","31");


//        Get get = new Get(Bytes.toBytes("r1"));
//        Result res1 = table.get(get);
//        System.out.println("Result:"+res1);
//
//        Result res2 = table.get(get);
//        System.out.println("Result:"+res2);
    }
}
