package org.farmer.coprocessor;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * User: mengxin
 * Date: 13-10-23
 * Time: 下午3:40
 */
public class AggregationOperator {
    private Configuration conf = HBaseConfiguration.create();

    private AggregationClient aggregationClient = new AggregationClient(conf);

    private LongColumnInterpreter columnInterpreter = new LongColumnInterpreter();

    private static final byte[] CF = Bytes.toBytes("f1");

    public double aggregate(String function,String tableName,Scan scan) throws Throwable{
        String[] s = function.split("\\(");
        scan.addColumn(CF, Bytes.toBytes("q1"));
        if(s[0].equals("count")){
            long rowCount = aggregationClient.rowCount(Bytes.toBytes(tableName),null,scan);
            return rowCount;
        }else if(s[0].equals("sum")){
            long sum = aggregationClient.sum(Bytes.toBytes(tableName), columnInterpreter, scan);
            return sum;
        }else if(s[0].equals("avg")){
            double avg = aggregationClient.avg(Bytes.toBytes(tableName), columnInterpreter, scan);
            return avg;
        }else if(s[0].equals("max")){
            long max = aggregationClient.max(Bytes.toBytes(tableName), columnInterpreter, scan);
            return max;
        }else if(s[0].equals("min")){
            long min = aggregationClient.min(Bytes.toBytes(tableName), columnInterpreter, scan);
            return min;
        }
        throw new Throwable("Method not supported");
    }
}
