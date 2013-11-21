package org.farmer.query;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.farmer.filter.FilterTree;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import org.farmer.parser.CommanderDispatcher;

/**
 * Created with IntelliJ IDEA.
 * User: zy
 * Date: 13-10-23
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class TestSql {
   // CommanderDispatcher parser = new CommanderDispatcher();
   // static long a=1,b=2,c=3,d=4,e=5,f=6,g=7;
    List<KeyValue> kvs = new ArrayList<KeyValue>();
    FilterTree ft;
   // static final Log logger =  LogFactory.getLog(TestSql.class);
    public void init(){
    //    parser.executeStatement("select count(q1) from testtable where q1=mengxin and q2=30");
        Integer r,q;
        Character f;
        for(r = 0; r < 12; r++)
            for (int i = 0; i < 7; i++) {
                f = (char) (i + 'a');
                q = i + 1 + r;
                KeyValue kv = new KeyValue(r.toString().getBytes(), f.toString().getBytes(), q.toString().getBytes());
                kvs.add(kv);
            }
       ft = new  FilterTree("select * from test where cf:a=1 and b=2 and c=3 and d=4 or e=5 and f=7");
    }



    public void test(){
        init();
        System.out.println("init ok");
        exe();

    }
    public void exe(){
        int all = kvs.size();
        List<KeyValue> tmpkvs = null;
        for(int i = 0; i < all; i++){
            KeyValue kv = kvs.get(i);
            if(ft.filterRowKey(kv.getKey(), kv.getKeyOffset(), kv.getKeyLength())){
                System.out.println("filterRowKey true continue");
                continue;
            }
            ft.filterKeyValue(kv);
            // ft.filterRow(tmpkvs);
            // ft.filterRow();
            if( ft.filterAllRemaining()){
                System.out.println("filterAllRemaining break");
               // break;
            }
        }

    }

   @Test
    public void xxhbase() throws IOException {
        HBaseConfiguration  conf = new HBaseConfiguration();
        conf.set("hbase.zookeeper.quorum",  "192.168.0.211");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("hbase.master", "192.168.0.211:44492");
                HTable table = null;
               try {
                  table = new HTable(conf, "test");

               } catch (IOException e) {
                   e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
               }
        System.out.println("HTable ok");
           Filter ff = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("a"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("1"));
       //    Filter ff = new FilterTree("select * from test where cf_a=1 and cf_b=2 ");
        System.out.println("HTable okx");
                Scan s = new Scan();
        System.out.println("HBaseConfiguration ok");
                s.setFilter(ff);
                // 指定之后，将只返回一个cell，也就是指定的那一个
                // s.addColumn(Bytes.toBytes("course"), Bytes.toBytes("art"));
                ResultScanner scanner;
                scanner = table.getScanner(s);

        System.out.println("HTable ok1");

                for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
                    System.out.println("rowkey = " + new String(rr.getRow()));
                    System.out.println("first cell value = " + rr.toString());

            }
       /*
        while(false){
            try {
               // Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        */
      }


}
