package org.farmer.query;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-7
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class CompareAndSetSample {
    public static void main(String[] args) throws Exception{
       Configuration conf = HBaseConfiguration.create();
       HTable table = new HTable(conf,"testtable");
       Put put1 = new Put(Bytes.toBytes("row1"));
       put1.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val1"));

       boolean res1 = table.checkAndPut(Bytes.toBytes("row1"),Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),null,put1);
       System.out.println("Put applied:"+res1);

       boolean res2 = table.checkAndPut(Bytes.toBytes("row1"),Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),null,put1);
       System.out.println("Put applied:"+res2);

       Put put2 = new Put(Bytes.toBytes("row1"));
       put2.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual2"),Bytes.toBytes("val2"));

       boolean res3 = table.checkAndPut(Bytes.toBytes("row1"),Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val"),put2);
       System.out.println("Put applied:"+res3);

       Put put3 = new Put(Bytes.toBytes("row2"));
       put3.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val3"));

       boolean res4 = table.checkAndPut(Bytes.toBytes("row1"),Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("val"),put3);
       System.out.println("Put applied:"+res4);
    }
}
