package org.farmer.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-9-22
 * Time: 下午6:46
 * To change this template use File | Settings | File Templates.
 */
public class PrefixFilterSample {
    public void filter() throws Exception{
        Configuration config = HBaseConfiguration.create();
        HTable table = new HTable(config,"sms");
        Filter filter = new PrefixFilter(Bytes.toBytes("row-1"));
        Scan scan = new Scan();
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        for(Result result:scanner){
            for(KeyValue kv:result.raw()){
                System.out.println("KV:"+kv+",Value:"+Bytes.toString(kv.getValue()));
            }
        }
        scanner.close();
        Get get = new Get(Bytes.toBytes("row-5"));
        get.setFilter(filter);
        Result result = table.get(get);
        for(KeyValue kv:result.raw()){
          System.out.println("KV:"+kv+",Value:"+Bytes.toString(kv.getValue()));
        }
    }
}
