package org.farmer.admin;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.util.Pair;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-9-30
 * Time: 下午10:03
 * To change this template use File | Settings | File Templates.
 */
public class PreTable {
    private static void printTableRegions(String tableName) throws IOException{
        Configuration conf = HBaseConfiguration.create();
        System.out.println("Printing regions of table:"+tableName);
        HTable table = new HTable(conf,Bytes.toBytes(tableName));
        Pair<byte[][],byte[][]> pair = table.getStartEndKeys();
        for(int n=0;n < pair.getFirst().length;n++){
            byte[] sk = pair.getFirst()[n];
            byte[] ek = pair.getSecond()[n];
            System.out.println("["+(n+1)+"]"+" start key: " + (sk.length==8?Bytes.toLong(sk):Bytes.toStringBinary(sk))+",end key:"+
                    (ek.length==8?Bytes.toLong(ek):Bytes.toStringBinary(ek)));
        }
    }

    public static void main(String[] args) throws IOException,InterruptedException{
        Configuration conf = HBaseConfiguration.create();
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor desc = new HTableDescriptor(Bytes.toBytes("testtable"));
        HColumnDescriptor coldef = new HColumnDescriptor(Bytes.toBytes("colfam1"));
        desc.addFamily(coldef);

        admin.createTable(desc,Bytes.toBytes(1L),Bytes.toBytes(100L),10);
        printTableRegions("testtable");

        byte[][] regions = new byte[][]{
            Bytes.toBytes("A"),
            Bytes.toBytes("D"),
            Bytes.toBytes("G"),
            Bytes.toBytes("K"),
            Bytes.toBytes("O"),
            Bytes.toBytes("T")
        };
        desc.setName(Bytes.toBytes("testtable2"));
        admin.createTable(desc,regions);
        printTableRegions("testtable2");

        HTableDescriptor[] htds = admin.listTables();
        for(HTableDescriptor htd:htds){
            System.out.println(htd);
        }

        HTableDescriptor htd1 = admin.getTableDescriptor(Bytes.toBytes("testtable"));
        System.out.println(htd1);

        HTableDescriptor htd2 = admin.getTableDescriptor(Bytes.toBytes("testtable10"));
        System.out.println(htd2);

        admin.deleteTable(Bytes.toBytes("testtable"));
        boolean isDisabled = admin.isTableDisabled(Bytes.toBytes("testtable"));
        System.out.println("Table is disabled:"+isDisabled);

        boolean avail = admin.isTableAvailable(Bytes.toBytes("testtable"));
        System.out.println("Table available:"+avail);

        admin.deleteTable(Bytes.toBytes("testtable"));

        boolean avail2 = admin.isTableAvailable(Bytes.toBytes("testtable"));
        System.out.println("Table available:"+avail2);

        boolean isEnable = admin.isTableEnabled(Bytes.toBytes("testtable"));

        admin.disableTable("testtable");
        htd1.setMaxFileSize(1024*1024*1024);
        admin.modifyTable(Bytes.toBytes("testtable"), htd1);
        admin.enableTable("testtable");

        admin.addColumn(Bytes.toBytes("testtable"),new HColumnDescriptor("add1"));
        admin.deleteColumn("testtable","add1");
        admin.modifyColumn("testtable",new HColumnDescriptor("abc"));
    }
}
