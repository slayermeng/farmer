package org.farmer.query;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.conf.Configuration;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-3
 * Time: 下午8:46
 * To change this template use File | Settings | File Templates.
 */
public class PutListSample {
    public static void main(String[] args) throws IOException{
        Configuration conf = HBaseConfiguration.create();
        HTable table = new HTable(conf,"testtable");
        List<Put> puts = new ArrayList<Put>();
        Put put1 = new Put(Bytes.toBytes("row1"));
        put1.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val1"));
        puts.add(put1);

        Put put2 = new Put(Bytes.toBytes("row2"));
        put2.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val2"));
        puts.add(put2);

        Put put3 = new Put(Bytes.toBytes("row2"));
        put3.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val3"));
        puts.add(put3);

        Put put4 = new Put(Bytes.toBytes("row2"));
        puts.add(put4);

        try{
            table.put(puts);
        }catch(Exception e){
            System.err.println("Error:"+e);
            table.flushCommits();
        }
    }
}
