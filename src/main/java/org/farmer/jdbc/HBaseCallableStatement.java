package org.farmer.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Array;
import java.sql.RowId;
import java.sql.ResultSetMetaData;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Ref;
import java.sql.NClob;
import java.sql.SQLXML;
import java.sql.Connection;
import java.sql.SQLWarning;
import java.sql.ResultSet;
import java.sql.ParameterMetaData;
import java.util.Calendar;
import java.util.Map;

/**
 * User: mengxin
 * Date: 13-10-29
 * Time: 下午7:55
 */
public class HBaseCallableStatement implements CallableStatement{
    public Array getArray(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void registerOutParameter(int i, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void registerOutParameter(int i, int i2, int i3) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean wasNull() throws SQLException {
        throw new SQLException("Method not supported");
    }

    public String getString(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public boolean getBoolean(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public byte getByte(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public short getShort(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public int getInt(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public long getLong(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public float getFloat(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public double getDouble(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public BigDecimal getBigDecimal(int i, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public byte[] getBytes(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Date getDate(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Time getTime(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Timestamp getTimestamp(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Object getObject(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public BigDecimal getBigDecimal(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Object getObject(int i, Map<String, Class<?>> stringClassMap) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Ref getRef(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Blob getBlob(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Clob getClob(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Date getDate(int i, Calendar calendar) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Time getTime(int i, Calendar calendar) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public Timestamp getTimestamp(int i, Calendar calendar) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void registerOutParameter(int i, int i2, String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void registerOutParameter(String s, int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void registerOutParameter(String s, int i, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void registerOutParameter(String s, int i, String s2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public URL getURL(int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setURL(String s, URL url) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNull(String s, int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBoolean(String s, boolean b) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setByte(String s, byte b) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setShort(String s, short i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setInt(String s, int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setLong(String s, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setFloat(String s, float v) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setDouble(String s, double v) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBigDecimal(String s, BigDecimal bigDecimal) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setString(String s, String s2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBytes(String s, byte[] bytes) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setDate(String s, Date date) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setTime(String s, Time time) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setTimestamp(String s, Timestamp timestamp) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setAsciiStream(String s, InputStream inputStream, int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBinaryStream(String s, InputStream inputStream, int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setObject(String s, Object o, int i, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setObject(String s, Object o, int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setObject(String s, Object o) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setCharacterStream(String s, Reader reader, int i) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setDate(String s, Date date, Calendar calendar) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setTime(String s, Time time, Calendar calendar) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setTimestamp(String s, Timestamp timestamp, Calendar calendar) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNull(String s, int i, String s2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public String getString(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public boolean getBoolean(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public byte getByte(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public short getShort(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public int getInt(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public long getLong(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public float getFloat(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public double getDouble(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public byte[] getBytes(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Date getDate(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Time getTime(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Timestamp getTimestamp(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Object getObject(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public BigDecimal getBigDecimal(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Object getObject(String s, Map<String, Class<?>> stringClassMap) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Ref getRef(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Blob getBlob(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Clob getClob(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Array getArray(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Date getDate(String s, Calendar calendar) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Time getTime(String s, Calendar calendar) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Timestamp getTimestamp(String s, Calendar calendar) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public URL getURL(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public RowId getRowId(int i) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public RowId getRowId(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public void setRowId(String s, RowId rowId) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNString(String s, String s2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNCharacterStream(String s, Reader reader, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNClob(String s, NClob nClob) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setClob(String s, Reader reader, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBlob(String s, InputStream inputStream, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNClob(String s, Reader reader, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public NClob getNClob(int i) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public NClob getNClob(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public void setSQLXML(String s, SQLXML sqlxml) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public SQLXML getSQLXML(int i) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public SQLXML getSQLXML(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public String getNString(int i) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public String getNString(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Reader getNCharacterStream(int i) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Reader getNCharacterStream(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Reader getCharacterStream(int i) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public Reader getCharacterStream(String s) throws SQLException {
          throw new SQLException("Method not supported");
    }

    public void setBlob(String s, Blob blob) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setClob(String s, Clob clob) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setAsciiStream(String s, InputStream inputStream, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBinaryStream(String s, InputStream inputStream, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setCharacterStream(String s, Reader reader, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setAsciiStream(String s, InputStream inputStream) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBinaryStream(String s, InputStream inputStream) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setCharacterStream(String s, Reader reader) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNCharacterStream(String s, Reader reader) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setClob(String s, Reader reader) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBlob(String s, InputStream inputStream) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setNClob(String s, Reader reader) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public ResultSet executeQuery() throws SQLException {
          throw new SQLException("Method not supported");
    }

    public int executeUpdate() throws SQLException {
          throw new SQLException("Method not supported");
    }

    public void setNull(int i, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBoolean(int i, boolean b) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setByte(int i, byte b) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setShort(int i, short i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setInt(int i, int i2) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setLong(int i, long l) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setFloat(int i, float v) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setDouble(int i, double v) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBigDecimal(int i, BigDecimal bigDecimal) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setString(int i, String s) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setBytes(int i, byte[] bytes) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setDate(int i, Date date) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setTime(int i, Time time) throws SQLException {
        throw new SQLException("Method not supported");
    }

    public void setTimestamp(int i, Timestamp timestamp) throws SQLException {
        throw new SQLException("Method not supported");
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
        throw new SQLException("Method not supported");
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
          throw new SQLException("Method not supported");
    }

    public void setMaxRows(int i) throws SQLException {
        throw new SQLException("Method not supported");
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
          throw new SQLException("Method not supported");
    }

    public int getUpdateCount() throws SQLException {
          throw new SQLException("Method not supported");
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
          throw new SQLException("Method not supported");
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
