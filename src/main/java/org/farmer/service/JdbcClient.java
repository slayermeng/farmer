package org.farmer.service;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.TException;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-16
 * Time: 上午10:09
 * To change this template use File | Settings | File Templates.
 */
public class JdbcClient {
    public static void main(String[] args){
        try{
            TTransport transport = new TSocket("localhost",7911);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            JdbcService.Client client = new JdbcService.Client(protocol);
            System.out.println(client.execute("123"));
            transport.close();
        }catch(TTransportException e){
            e.printStackTrace();
        }catch(TException ex){
            ex.printStackTrace();
        }
    }
}
