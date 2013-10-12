package org.farmer.admin;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * User: mengxin
 * Date: 13-9-30
 * Time: 下午9:40
 */
public class HBaseAdminFacade {
    public static Configuration conf = HBaseConfiguration.create();

    public HBaseAdmin admin;

    public HBaseAdminFacade() throws MasterNotRunningException,ZooKeeperConnectionException{
        admin = new HBaseAdmin(conf);
    }

    public boolean isMasterRunning() throws MasterNotRunningException,ZooKeeperConnectionException{
        return admin.isMasterRunning();
    }

    public HConnection getConnection(){
        return admin.getConnection();
    }

    public boolean isTableAvailable(String tableName) throws IOException{
        return admin.isTableAvailable(Bytes.toBytes(tableName));
    }

    public void createTable(String tableName,String... family) throws IOException{
        HTableDescriptor desc = new HTableDescriptor(Bytes.toBytes(tableName));
        for(String f:family){
            HColumnDescriptor coldef = new HColumnDescriptor(Bytes.toBytes(f));
            desc.addFamily(coldef);
        }
        admin.createTable(desc);
    }

    public static void main(String[] args) throws Exception{
        HBaseAdminFacade has = new HBaseAdminFacade();
        System.out.println("Master Running:"+has.isMasterRunning());//is master running
        has.createTable("testtable","f1","f2");
        System.out.println("test available:"+has.isTableAvailable("test"));
    }
}
