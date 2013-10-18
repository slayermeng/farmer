package org.farmer.jdbc;

import org.farmer.service.JdbcService;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mengxin
 * Date: 13-10-10
 * Time: 上午10:08
 */
public class HBasePreparedStatement implements PreparedStatement {
    private String sql;

    private final Map<Integer, String> parameters=new HashMap<Integer, String>();

    private ResultSet resultSet = null;

    private JdbcService.Iface client;

    private boolean isClosed = false;

    private  int maxRows = 0;

    private final int updateCount=0;

    public HBasePreparedStatement(String sql,JdbcService.Iface client){
        this.sql = sql;
        this.client = client;
    }


    public ResultSet executeQuery() throws SQLException {
        if (isClosed) {
            throw new SQLException("Can't execute after statement has been closed");
        }

        try {
            resultSet = null;
            if (sql.contains("?")) {
                sql = updateSql(sql, parameters);
            }
            client.execute(sql);
        } catch (Exception ex) {
            throw new SQLException(ex.toString(), "08S01", ex);
        }
        resultSet = new HBaseResultSet(client, maxRows);
        return resultSet;
    }

    private String updateSql(final String sql, Map<Integer, String> parameters) {

        StringBuffer newSql = new StringBuffer(sql);

        int paramLoc = 1;
        while (getCharIndexFromSqlByParamLocation(sql, '?', paramLoc) > 0) {
            // check the user has set the needs parameters
            if (parameters.containsKey(paramLoc)) {
                int tt = getCharIndexFromSqlByParamLocation(newSql.toString(), '?', 1);
                newSql.deleteCharAt(tt);
                newSql.insert(tt, parameters.get(paramLoc));
            }
            paramLoc++;
        }

        return newSql.toString();

    }

    private int getCharIndexFromSqlByParamLocation(final String sql, final char cchar, final int paramLoc) {
        int signalCount = 0;
        int charIndex = -1;
        int num = 0;
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if (c == '\'' || c == '\\')// record the count of char "'" and char "\"
            {
                signalCount++;
            } else if (c == cchar && signalCount % 2 == 0) {// check if the ? is really the parameter
                num++;
                if (num == paramLoc) {
                    charIndex = i;
                    break;
                }
            }
        }
        return charIndex;
    }

    public int executeUpdate() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNull(int i, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBoolean(int parameterIndex, boolean v) throws SQLException {
        this.parameters.put(parameterIndex, String.valueOf(v));
    }

    public void setByte(int parameterIndex, byte v) throws SQLException {
        this.parameters.put(parameterIndex, String.valueOf(v));
    }

    public void setShort(int parameterIndex, short v) throws SQLException {
        this.parameters.put(parameterIndex, String.valueOf(v));
    }

    public void setInt(int parameterIndex, int v) throws SQLException {
        this.parameters.put(parameterIndex, String.valueOf(v));
    }

    public void setLong(int parameterIndex, long v) throws SQLException {
        this.parameters.put(parameterIndex, String.valueOf(v));
    }

    public void setFloat(int parameterIndex, float v) throws SQLException {
        this.parameters.put(parameterIndex, String.valueOf(v));
    }

    public void setDouble(int parameterIndex, double v) throws SQLException {
        this.parameters.put(parameterIndex, String.valueOf(v));
    }

    public void setBigDecimal(int i, BigDecimal bigDecimal) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        x=x.replace("'", "\\'");
        this.parameters.put(parameterIndex,"'"+x+"'");
    }

    public void setBytes(int i, byte[] bytes) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setDate(int i, Date date) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setTime(int parameterIndex, Time time) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setTimestamp(int parameterIndex, Timestamp timestamp) throws SQLException {
        this.parameters.put(parameterIndex, timestamp.toString());
    }

    public void setAsciiStream(int i, InputStream inputStream, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setUnicodeStream(int i, InputStream inputStream, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBinaryStream(int i, InputStream inputStream, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void clearParameters() throws SQLException {
        this.parameters.clear();
    }

    public void setObject(int i, Object o, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setObject(int i, Object o) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean execute() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void addBatch() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setCharacterStream(int i, Reader reader, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setRef(int i, Ref ref) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBlob(int i, Blob blob) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setClob(int i, Clob clob) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setArray(int i, Array array) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setDate(int i, Date date, Calendar calendar) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setTime(int i, Time time, Calendar calendar) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setTimestamp(int i, Timestamp timestamp, Calendar calendar) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNull(int i, int i2, String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setURL(int i, URL url) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setRowId(int i, RowId rowId) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNString(int i, String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNCharacterStream(int i, Reader reader, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNClob(int i, NClob nClob) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setClob(int i, Reader reader, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBlob(int i, InputStream inputStream, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNClob(int i, Reader reader, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setSQLXML(int i, SQLXML sqlxml) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setObject(int i, Object o, int i2, int i3) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setAsciiStream(int i, InputStream inputStream, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBinaryStream(int i, InputStream inputStream, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setCharacterStream(int i, Reader reader, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setAsciiStream(int i, InputStream inputStream) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBinaryStream(int i, InputStream inputStream) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setCharacterStream(int i, Reader reader) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNCharacterStream(int i, Reader reader) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setClob(int i, Reader reader) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBlob(int i, InputStream inputStream) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNClob(int i, Reader reader) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public ResultSet executeQuery(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int executeUpdate(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void close() throws SQLException {
        throw new SQLException("Method not supported");
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
        this.maxRows = max;
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
        throw new SQLException("Method not supported");
    }

    public void setCursorName(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean execute(String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public ResultSet getResultSet() throws SQLException {
        return resultSet;
    }

    public int getUpdateCount() throws SQLException {
        return updateCount;
    }

    public boolean getMoreResults() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setFetchDirection(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int getFetchDirection() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setFetchSize(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int getFetchSize() throws SQLException {
        throw new SQLException("Method not supported");
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
}
