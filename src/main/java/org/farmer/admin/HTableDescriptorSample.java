package org.farmer.admin;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-9-30
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
public class HTableDescriptorSample {
    public static void main(String[] args) throws Exception{
        HTableDescriptor table = new HTableDescriptor();
        //table name
        table.setName(Bytes.toBytes("mengxin"));
        table.getNameAsString();
        //table family
        table.addFamily(new HColumnDescriptor("a"));
        table.hasFamily(Bytes.toBytes("a"));
        table.getColumnFamilies();
        table.getFamily(Bytes.toBytes("123"));
        table.remove(Bytes.toBytes("a"));
        //file size
        table.getMaxFileSize();
        table.setMaxFileSize(100L);
        //only read
        table.isReadOnly();
        table.setReadOnly(true);
        //flush
        table.getMemStoreFlushSize();
        table.setMemStoreFlushSize(100L);
        //delay wal
        table.isDeferredLogFlush();
        table.setDeferredLogFlush(true);
        //other
        table.getValue("123");
        table.setValue("123","456");
        table.remove("123");
    }
}
