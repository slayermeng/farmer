package org.farmer.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Get;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-9-22
 * Time: 下午7:20
 * To change this template use File | Settings | File Templates.
 */
public class QualifierFilterSample {
    public void filter() throws Exception{
        Configuration config = HBaseConfiguration.create();
        HTable table = new HTable(config,"sms");
        Filter filter = new QualifierFilter(CompareFilter.CompareOp.LESS_OR_EQUAL,new BinaryComparator(Bytes.toBytes("col-2")));
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        for(Result result:scanner){
            System.out.println(result);
        }
        scanner.close();
        Get get = new Get(Bytes.toBytes("row-5"));
        get.setFilter(filter);
        Result result = table.get(get);
        System.out.println("Result of get():"+result);
    }
}
