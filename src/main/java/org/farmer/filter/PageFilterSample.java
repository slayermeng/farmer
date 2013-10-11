package org.farmer.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Result;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-9-22
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 */
public class PageFilterSample {
    public void page() throws Exception{
        Configuration config = HBaseConfiguration.create();
        final byte[] POSTFIX = new byte[] { 0x00 };
        HTable table = new HTable(config,"sms");
        Filter filter = new PageFilter(15);
        int totalRows = 0;
        byte[] lastRow = null;
        while(true){
            Scan scan = new Scan();
            scan.setFilter(filter);
            if(lastRow != null){
                byte[] startRow = Bytes.add(lastRow,POSTFIX);
                System.out.println("start row:"+Bytes.toStringBinary(startRow));
                scan.setStartRow(startRow);
            }
            ResultScanner scanner = table.getScanner(scan);
            int localRows = 0;
            Result result = null;
            while((result = scanner.next())!=null){
                System.out.println(localRows++ + ":"+result);
                totalRows++;
                lastRow = result.getRow();
            }
            scanner.close();
            if(localRows == 0) break;
        }
        System.out.println("total rows:"+totalRows);
    }

    public static void main(String[] args) throws Exception{

    }
}
