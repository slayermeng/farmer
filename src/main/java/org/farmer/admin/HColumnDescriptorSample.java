package org.farmer.admin;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.io.hfile.Compression;
import org.apache.hadoop.hbase.regionserver.StoreFile;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-9-30
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 */
public class HColumnDescriptorSample{
    public static void main(String[] args) throws Exception{
        HColumnDescriptor column = new HColumnDescriptor(Bytes.toBytes("abc"));
        //name
        column.getName();
        column.getNameAsString();
        //max version
        column.setMaxVersions(1);
        column.getMaxVersions();
        //compression
        column.getCompactionCompression();
        column.getCompactionCompressionType();
        column.setCompressionType(Compression.Algorithm.SNAPPY);
        //block size
        column.getBlocksize();
        column.setBlocksize(64*1024);
        //cache
        column.isBlockCacheEnabled();
        column.setBlockCacheEnabled(false);
        //ttl
        column.getTimeToLive();
        column.setTimeToLive(100);
        //in-memory
        column.isInMemory();
        column.setInMemory(true);
        //bloom
        column.getBloomFilterType();
        column.setBloomFilterType(StoreFile.BloomType.ROW);
        //replication
        column.getScope();
        column.setScope(1);
    }
}
