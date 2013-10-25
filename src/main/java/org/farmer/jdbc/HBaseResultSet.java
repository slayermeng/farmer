package org.farmer.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.farmer.service.JdbcService;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Ref;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.Array;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Time;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.SQLWarning;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-10
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
public class HBaseResultSet implements ResultSet {
    private static final Log LOG = LogFactory.getLog(HBaseResultSet.class);

    private JdbcService.Iface client;

    private  int maxRows = 0;

    protected boolean wasNull = false;

    protected List<Object> row;

    protected List<String> columnNames;

    protected List<String> columnTypes;

    private int rowsFetched = 0;

    private int fetchSize = 50;

    private List<String> fetchedRows;

    private Iterator<String> fetchedRowsItr;

    public HBaseResultSet(JdbcService.Iface client,int maxRows){
        this.client = client;
        this.maxRows = maxRows;
        row = Arrays.asList(new Object[columnNames.size()]);
    }

    //TODO get方法实现
    public boolean next() throws SQLException {
//        if (maxRows > 0 && rowsFetched >= maxRows) {
//            return false;
//        }
//
//        try {
//            if (fetchedRows == null || !fetchedRowsItr.hasNext()) {
//                fetchedRows = client.fetchN(fetchSize);
//                fetchedRowsItr = fetchedRows.iterator();
//            }
//
//            String rowStr = "";
//            if (fetchedRowsItr.hasNext()) {
//                rowStr = fetchedRowsItr.next();
//            } else {
//                return false;
//            }
//
//            rowsFetched++;
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("Fetched row string: " + rowStr);
//            }
//
//            StructObjectInspector soi = (StructObjectInspector) serde.getObjectInspector();
//            List<? extends StructField> fieldRefs = soi.getAllStructFieldRefs();
//            Object data = serde.deserialize(new BytesWritable(rowStr.getBytes()));
//
//            assert row.size() == fieldRefs.size() : row.size() + ", " + fieldRefs.size();
//            for (int i = 0; i < fieldRefs.size(); i++) {
//                StructField fieldRef = fieldRefs.get(i);
//                ObjectInspector oi = fieldRef.getFieldObjectInspector();
//                Object obj = soi.getStructFieldData(data, fieldRef);
//                row.set(i, convertLazyToJava(obj, oi));
//            }
//
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("Deserialized row: " + row);
//            }
//        } catch (HiveServerException e) {
//            if (e.getErrorCode() == 0) { // error code == 0 means reached the EOF
//                return false;
//            } else {
//                throw new SQLException("Error retrieving next row", e);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new SQLException("Error retrieving next row", ex);
//        }
        return true;
    }

    public void close() throws SQLException {
        client = null;
    }

    public boolean wasNull() throws SQLException {
        return wasNull;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getString(int columnIndex) throws SQLException {
        Object obj = getObject(columnIndex);
        if (obj == null) {
            return null;
        }

        return obj.toString();
    }

    public boolean getBoolean(int columnIndex) throws SQLException {
        Object obj = getObject(columnIndex);
        if (Boolean.class.isInstance(obj)) {
            return (Boolean) obj;
        } else if (obj == null) {
            return false;
        } else if (Number.class.isInstance(obj)) {
            return ((Number) obj).intValue() != 0;
        } else if (String.class.isInstance(obj)) {
            return !((String) obj).equals("0");
        }
        throw new SQLException("Cannot convert column " + columnIndex + " to boolean");
    }

    public byte getByte(int i) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public short getShort(int i) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getInt(int i) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getLong(int i) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public float getFloat(int i) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double getDouble(int i) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BigDecimal getBigDecimal(int i, int i2) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public byte[] getBytes(int i) throws SQLException {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Date getDate(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Time getTime(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Timestamp getTimestamp(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public InputStream getAsciiStream(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public InputStream getUnicodeStream(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public InputStream getBinaryStream(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getString(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean getBoolean(String s) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public byte getByte(String s) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public short getShort(String s) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getInt(String s) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getLong(String s) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public float getFloat(String s) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double getDouble(String s) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BigDecimal getBigDecimal(String s, int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public byte[] getBytes(String s) throws SQLException {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Date getDate(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Time getTime(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Timestamp getTimestamp(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public InputStream getAsciiStream(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public InputStream getUnicodeStream(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public InputStream getBinaryStream(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SQLWarning getWarnings() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clearWarnings() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getCursorName() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getObject(int columnIndex) throws SQLException {
        if (row == null) {
            throw new SQLException("No row found.");
        }

        if (columnIndex > row.size()) {
            throw new SQLException("Invalid columnIndex: " + columnIndex);
        }

        try {
            wasNull = false;
            if (row.get(columnIndex - 1) == null) {
                wasNull = true;
            }

            return row.get(columnIndex - 1);
        } catch (Exception e) {
            throw new SQLException(e.toString());
        }
    }

    public Object getObject(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int findColumn(String s) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Reader getCharacterStream(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Reader getCharacterStream(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BigDecimal getBigDecimal(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BigDecimal getBigDecimal(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isBeforeFirst() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isAfterLast() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isFirst() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isLast() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void beforeFirst() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void afterLast() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean first() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean last() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getRow() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean absolute(int i) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean relative(int i) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean previous() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setFetchDirection(int i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getFetchDirection() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setFetchSize(int i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getFetchSize() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getType() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getConcurrency() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean rowUpdated() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean rowInserted() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean rowDeleted() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNull(int i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBoolean(int i, boolean b) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateByte(int i, byte b) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateShort(int i, short i2) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateInt(int i, int i2) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateLong(int i, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateFloat(int i, float v) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateDouble(int i, double v) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBigDecimal(int i, BigDecimal bigDecimal) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateString(int i, String s) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBytes(int i, byte[] bytes) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateDate(int i, Date date) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateTime(int i, Time time) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateTimestamp(int i, Timestamp timestamp) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateAsciiStream(int i, InputStream inputStream, int i2) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBinaryStream(int i, InputStream inputStream, int i2) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateCharacterStream(int i, Reader reader, int i2) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateObject(int i, Object o, int i2) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateObject(int i, Object o) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNull(String s) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBoolean(String s, boolean b) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateByte(String s, byte b) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateShort(String s, short i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateInt(String s, int i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateLong(String s, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateFloat(String s, float v) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateDouble(String s, double v) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBigDecimal(String s, BigDecimal bigDecimal) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateString(String s, String s2) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBytes(String s, byte[] bytes) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateDate(String s, Date date) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateTime(String s, Time time) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateTimestamp(String s, Timestamp timestamp) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateAsciiStream(String s, InputStream inputStream, int i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBinaryStream(String s, InputStream inputStream, int i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateCharacterStream(String s, Reader reader, int i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateObject(String s, Object o, int i) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateObject(String s, Object o) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void insertRow() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateRow() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteRow() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void refreshRow() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void cancelRowUpdates() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void moveToInsertRow() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void moveToCurrentRow() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Statement getStatement() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getObject(int i, Map<String, Class<?>> stringClassMap) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Ref getRef(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Blob getBlob(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Clob getClob(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Array getArray(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getObject(String s, Map<String, Class<?>> stringClassMap) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Ref getRef(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Blob getBlob(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Clob getClob(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Array getArray(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Date getDate(int i, Calendar calendar) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Date getDate(String s, Calendar calendar) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Time getTime(int i, Calendar calendar) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Time getTime(String s, Calendar calendar) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Timestamp getTimestamp(int i, Calendar calendar) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Timestamp getTimestamp(String s, Calendar calendar) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public URL getURL(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public URL getURL(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateRef(int i, Ref ref) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateRef(String s, Ref ref) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBlob(int i, Blob blob) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBlob(String s, Blob blob) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateClob(int i, Clob clob) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateClob(String s, Clob clob) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateArray(int i, Array array) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateArray(String s, Array array) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public RowId getRowId(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RowId getRowId(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateRowId(int i, RowId rowId) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateRowId(String s, RowId rowId) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getHoldability() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isClosed() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNString(int i, String s) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNString(String s, String s2) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNClob(int i, NClob nClob) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNClob(String s, NClob nClob) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public NClob getNClob(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public NClob getNClob(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SQLXML getSQLXML(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SQLXML getSQLXML(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateSQLXML(int i, SQLXML sqlxml) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateSQLXML(String s, SQLXML sqlxml) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getNString(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getNString(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Reader getNCharacterStream(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Reader getNCharacterStream(String s) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNCharacterStream(int i, Reader reader, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNCharacterStream(String s, Reader reader, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateAsciiStream(int i, InputStream inputStream, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBinaryStream(int i, InputStream inputStream, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateCharacterStream(int i, Reader reader, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateAsciiStream(String s, InputStream inputStream, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBinaryStream(String s, InputStream inputStream, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateCharacterStream(String s, Reader reader, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBlob(int i, InputStream inputStream, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBlob(String s, InputStream inputStream, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateClob(int i, Reader reader, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateClob(String s, Reader reader, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNClob(int i, Reader reader, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNClob(String s, Reader reader, long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNCharacterStream(int i, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNCharacterStream(String s, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateAsciiStream(int i, InputStream inputStream) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBinaryStream(int i, InputStream inputStream) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateCharacterStream(int i, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateAsciiStream(String s, InputStream inputStream) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBinaryStream(String s, InputStream inputStream) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateCharacterStream(String s, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBlob(int i, InputStream inputStream) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateBlob(String s, InputStream inputStream) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateClob(int i, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateClob(String s, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNClob(int i, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateNClob(String s, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public <T> T unwrap(Class<T> tClass) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
