package org.farmer.service;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer.Args;

/**
 * User: mengxin
 * Date: 13-10-15
 * Time: 下午4:37
 */
public class JdbcServiceServer {
    public static void main(String[] args) throws Exception{
        TServerSocket serverTransport = new TServerSocket(7911);
        JdbcService.Processor processor = new JdbcService.Processor(new JdbcServiceImpl());
        Factory portFactory = new TBinaryProtocol.Factory(true, true);
        Args arg = new Args(serverTransport);
        arg.processor(processor);
        arg.protocolFactory(portFactory);
        TServer server = new TThreadPoolServer(arg);
        System.out.println("Starting server on port 7911 ...");

        server.serve();
    }
}
