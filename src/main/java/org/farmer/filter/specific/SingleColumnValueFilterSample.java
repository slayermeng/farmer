package org.farmer.filter.specific;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.KeyValue;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-9-23
 * Time: 下午4:21
 * To change this template use File | Settings | File Templates.
 */
public class SingleColumnValueFilterSample {
    public void filter() throws Exception{
        Configuration config = HBaseConfiguration.create();
        HTable table = new HTable(config,"testtable");
        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("f1"),
                Bytes.toBytes("q1"),
                CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes("guanzy")));
        filter.setFilterIfMissing(true);
        Scan scan = new Scan();
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        for(Result result:scanner){
            for(KeyValue kv:result.raw()){
                System.out.println("KV:"+kv+",Value:"+Bytes.toString(kv.getValue()));
            }
        }
    }

    public static void main(String[] args) throws Exception{
        SingleColumnValueFilterSample svf = new SingleColumnValueFilterSample();
        svf.filter();
    }

}
