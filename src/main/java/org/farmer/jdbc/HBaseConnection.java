package org.farmer.jdbc;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.TException;

import java.util.concurrent.Executor;
import java.sql.*;
import java.util.Map;
import java.util.Properties;

import org.farmer.service.JdbcService.Client;
import org.farmer.service.JdbcService.Iface;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-10
 * Time: 上午10:01
 * To change this template use File | Settings | File Templates.
 */
public class HBaseConnection implements Connection {
    private TTransport transport;

    private Iface client;

    private boolean isClosed = true;

    private static final String URL_PREFIX = "jdbc:hbase://";

    public HBaseConnection(String url, Properties info) throws SQLException {
        if (!url.startsWith(URL_PREFIX)) {
            throw new SQLException("Invalid URL:" + url, "08S01");
        }

        url = url.substring(URL_PREFIX.length());
        if (!url.isEmpty()) {
            String[] hostport = url.split(":");
            int port = 10004;
            String host = hostport[0];
            try {
                port = Integer.parseInt(hostport[1]);
            } catch (Exception e) {
                throw new SQLException("Invalid Port:" + port, "08S01");
            }
            TTransport transport = new TSocket(host, port);
            TProtocol protocol = new TBinaryProtocol(transport);
            //TODO init client
            client = new Client(protocol);
            try {
                transport.open();
            } catch (TTransportException e) {
                throw new SQLException("Could not establish connection to "
                        + url + ": " + e.getMessage(), "08S01");
            }
        }
        isClosed = false;
    }

    /**
     * for jdk1.7
     *
     * @param executor
     * @throws SQLException
     */
    public void abort(Executor executor) throws SQLException {
        throw new SQLException("Method not supported");
    }

    /**
     * for jdk1.7
     *
     * @return
     * @throws SQLException
     */
    public int getNetworkTimeout() throws SQLException {
        throw new SQLException("Method not supported");
    }

    /**
     * for jdk1.7
     *
     * @param executor
     * @param milliseconds
     * @throws SQLException
     */
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Statement createStatement() throws SQLException {
        if (isClosed) {
            throw new SQLException("Can't create Statement, connection is closed");
        }
        return new HBaseStatement(client);
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new HBasePreparedStatement(sql, client);
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public CallableStatement prepareCall(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public String nativeSQL(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setAutoCommit(boolean b) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean getAutoCommit() throws SQLException {
        return true;
    }

    public void commit() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void rollback() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void close() throws SQLException {
        if (!isClosed) {
            if (transport != null) {
                transport.close();
            }
        }
    }

    public boolean isClosed() throws SQLException {
        return isClosed;
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        //TODO metadata
        return null;
    }

    public void setReadOnly(boolean b) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean isReadOnly() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setCatalog(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public String getCatalog() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setTransactionIsolation(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int getTransactionIsolation() throws SQLException {
        return Connection.TRANSACTION_NONE;
    }

    public SQLWarning getWarnings() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void clearWarnings() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public CallableStatement prepareCall(String s, int i, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setTypeMap(Map<String, Class<?>> stringClassMap) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setHoldability(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int getHoldability() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Savepoint setSavepoint() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Savepoint setSavepoint(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public CallableStatement prepareCall(String s, int i, int i2, int i3) throws SQLException {
        throw new SQLException("Method not supported");
    }


    public Clob createClob() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Blob createBlob() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public NClob createNClob() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public SQLXML createSQLXML() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean isValid(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setClientInfo(String s, String s2) throws SQLClientInfoException {
        throw new SQLClientInfoException("Method not supported", null);
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        throw new SQLClientInfoException("Method not supported", null);
    }

    public String getClientInfo(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Properties getClientInfo() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Array createArrayOf(String s, Object[] objects) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Struct createStruct(String s, Object[] objects) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public <T> T unwrap(Class<T> tClass) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        throw new SQLException("Method not supported");
    }
}
