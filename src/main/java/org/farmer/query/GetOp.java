package org.farmer.query;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-7
 * Time: 下午5:22
 * To change this template use File | Settings | File Templates.
 */
public class GetOp {
    private static Configuration conf  = HBaseConfiguration.create();

    private HTable table = null;

    public GetOp(String tableName) throws IOException{
        table = new HTable(conf,tableName);
    }

    public String fetchOne(String rowkey,String family,String column) throws IOException{
        Get get = new Get(Bytes.toBytes(rowkey));
        get.addColumn(Bytes.toBytes(family),Bytes.toBytes(column));
        Result result = table.get(get);
        byte[] val = result.getValue(Bytes.toBytes(family),Bytes.toBytes(column));
        return Bytes.toString(val);
    }
}
