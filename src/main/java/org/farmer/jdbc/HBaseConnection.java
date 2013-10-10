package org.farmer.jdbc;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-10
 * Time: 上午10:01
 * To change this template use File | Settings | File Templates.
 */
public class HBaseConnection implements Connection {
    private boolean isClosed = true;

    public HBaseConnection(String url, Properties info) {
        //todo connection server
    }

    @Override
    public Statement createStatement() throws SQLException {
        return new HBaseStatement();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new HBasePreparedStatement(sql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public CallableStatement prepareCall(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public String nativeSQL(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void setAutoCommit(boolean b) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void commit() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void rollback() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void close() throws SQLException {
        //todo close client
    }

    @Override
    public boolean isClosed() throws SQLException {
        return isClosed;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setReadOnly(boolean b) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setCatalog(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public String getCatalog() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void setTransactionIsolation(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return Connection.TRANSACTION_NONE;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public CallableStatement prepareCall(String s, int i, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> stringClassMap) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void setHoldability(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public Savepoint setSavepoint(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new SQLException("Method not supported");
    }


    @Override
    public CallableStatement prepareCall(String s, int i, int i2, int i3) throws SQLException {
        throw new SQLException("Method not supported");
    }


    @Override
    public Clob createClob() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public Blob createBlob() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public NClob createNClob() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public boolean isValid(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void setClientInfo(String s, String s2) throws SQLClientInfoException {
        throw new SQLClientInfoException("Method not supported", null);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        throw new SQLClientInfoException("Method not supported", null);
    }

    @Override
    public String getClientInfo(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public Array createArrayOf(String s, Object[] objects) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public Struct createStruct(String s, Object[] objects) throws SQLException {
        throw new SQLException("Method not supported");
    }


    @Override
    public <T> T unwrap(Class<T> tClass) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        throw new SQLException("Method not supported");
    }
}
