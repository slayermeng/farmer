package org.farmer.service;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-18
 * Time: 下午5:50
 * To change this template use File | Settings | File Templates.
 */
public class HBaseServer {
    public static void main(String[] args){
        try{
            TServerSocket serverTransport = new TServerSocket(Integer.parseInt(args[0]));
            JdbcService.Processor processor = new JdbcService.Processor(new JdbcServiceImpl());
            TBinaryProtocol.Factory portFactory = new TBinaryProtocol.Factory(true, true);
            TThreadPoolServer.Args arg = new TThreadPoolServer.Args(serverTransport);
            arg.processor(processor);
            arg.protocolFactory(portFactory);
            TServer server = new TThreadPoolServer(arg);
            System.out.println("Starting server on port " + args[0] +" ...");
            server.serve();
        }catch(TTransportException e){
            e.printStackTrace();
        }

    }
}
