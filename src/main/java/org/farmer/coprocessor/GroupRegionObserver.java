package org.farmer.coprocessor;

import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.RegionScanner;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

/**
 * User: mengxin
 * Date: 13-11-11
 * Time: 下午3:13
 */
public class GroupRegionObserver extends BaseRegionObserver {
    public RegionScanner postScannerOpen(final ObserverContext<RegionCoprocessorEnvironment> e,
                                         final Scan scan, final RegionScanner s) throws IOException {
        Map<String,String> aggregateMap = new HashMap<String,String>();
        byte[] key = scan.getAttribute("groupby");//group条件
        List<KeyValue> results = new ArrayList();
        boolean hasMore = false;
        do{
            hasMore = s.nextRaw(results,null);
            if(!results.isEmpty()){
                Iterator it = results.iterator();
                while(it.hasNext()){
                    KeyValue result = (KeyValue)it.next();
                    if(aggregateMap.get(result.getKeyString())==null){
                        aggregateMap.put(result.getKeyString(), Bytes.toString(result.getValue()));
                    }else{
                        aggregateMap.put(result.getKeyString(),aggregateMap.get(result.getKeyString())+result.getValue());
                    }
                }
            }
        }while(hasMore);
        final List<KeyValue> aggResults = new ArrayList<KeyValue>(aggregateMap.size());
        RegionScanner scanner = new BaseRegionScanner() {
            private int index = 0;

            public HRegionInfo getRegionInfo() {
                return s.getRegionInfo();
            }

            public boolean next(List<KeyValue> result) throws IOException {
                if(aggResults.size()>index) return false;
                result.add(aggResults.get(index));
                index++;
                return index < aggResults.size();
            }

            public void close() throws IOException {
                s.close();
            }
        };
        return scanner;
    }
}
