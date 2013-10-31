package org.farmer.jdbc;

import org.farmer.service.JdbcService;

import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import org.apache.thrift.TException;

/**
 * Implement java.sql.Statement
 */
public class HBaseStatement implements Statement {
    private JdbcService.Iface client;

    private boolean isClosed = false;

    private ResultSet resultSet = null;

    private int fetchSize = 50;

    private int maxRows=0;

    private SQLWarning warningChain = null;

    public HBaseStatement(JdbcService.Iface client){
        this.client = client;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        if (isClosed) {
            throw new SQLException("Can't execute after statement has been closed");
        }
        try{
            resultSet = null;
            client.execute(sql);
        }catch(TException ex){
            throw new SQLException(ex.toString(), "08S01");
        }

        resultSet = new HBaseResultSet(client, maxRows);
        resultSet.setFetchSize(fetchSize);
        return resultSet;
    }

    public int executeUpdate(String sql) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void close() throws SQLException {
        client = null;
        resultSet = null;
        isClosed = true;
    }

    public int getMaxFieldSize() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setMaxFieldSize(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int getMaxRows() throws SQLException {
        return maxRows;
    }

    public void setMaxRows(int max) throws SQLException {
        if (max < 0) {
            throw new SQLException("max must be >= 0");
        }
        maxRows = max;
    }

    public void setEscapeProcessing(boolean b) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int getQueryTimeout() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setQueryTimeout(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void cancel() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public SQLWarning getWarnings() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void clearWarnings() throws SQLException {
        warningChain = null;
    }

    public void setCursorName(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean execute(String sql) throws SQLException {
        //todo excute含义较多，对于Select看结果集是否为null,如果是其它操作看影响的条数是否为0
        ResultSet rs = executeQuery(sql);
        return rs != null;
    }

    public ResultSet getResultSet() throws SQLException {
        return resultSet;
    }

    public int getUpdateCount() throws SQLException {
        return 0;
    }

    public boolean getMoreResults() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setFetchDirection(int rows) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int getFetchDirection() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setFetchSize(int rows) throws SQLException {
        fetchSize = rows;
    }

    public int getFetchSize() throws SQLException {
        return fetchSize;
    }

    public int getResultSetConcurrency() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int getResultSetType() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void addBatch(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void clearBatch() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int[] executeBatch() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Connection getConnection() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean getMoreResults(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int executeUpdate(String s, int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int executeUpdate(String s, int[] ints) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int executeUpdate(String s, String[] strings) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean execute(String s, int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean execute(String s, int[] ints) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean execute(String s, String[] strings) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int getResultSetHoldability() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean isClosed() throws SQLException {
        return isClosed;
    }

    public void setPoolable(boolean b) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean isPoolable() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public <T> T unwrap(Class<T> tClass) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        throw new SQLException("Method not supported");
    }

    /**
     * for jdk1.7
     * @throws SQLException
     */
    public void closeOnCompletion() throws SQLException {
        throw new SQLException("Method not supported");
    }
}
