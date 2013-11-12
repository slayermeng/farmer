package org.farmer.coprocessor;

import org.apache.hadoop.hbase.DoNotRetryIOException;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.regionserver.RegionScanner;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-11-11
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseRegionScanner implements RegionScanner {

    public boolean isFilterDone() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean reseek(byte[] bytes) throws IOException {
        throw new DoNotRetryIOException("Unsupported");
    }

    public long getMvccReadPoint() {
        return Long.MAX_VALUE;
    }

    public boolean nextRaw(List<KeyValue> keyValues, int i, String s) throws IOException {
        return next(keyValues,i,s);
    }

    public boolean nextRaw(List<KeyValue> keyValues, String s) throws IOException {
        return next(keyValues,s);
    }

    public boolean next(List<KeyValue> keyValues, int i) throws IOException {
        return next(keyValues);
    }

    public boolean next(List<KeyValue> keyValues, int i, String s) throws IOException {
        return next(keyValues);
    }

    public boolean next(List<KeyValue> keyValues, String s) throws IOException {
        return next(keyValues);
    }
}
