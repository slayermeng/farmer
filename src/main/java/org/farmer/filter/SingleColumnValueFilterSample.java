package org.farmer.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.KeyValue;

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
        HTable table = new HTable(config,"sms");
        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("colfam1"),
                Bytes.toBytes("col-5"),
                CompareFilter.CompareOp.NOT_EQUAL,
                new SubstringComparator("val-5"));
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
}
