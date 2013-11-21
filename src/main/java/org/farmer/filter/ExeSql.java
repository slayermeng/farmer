package org.farmer.filter;

import net.sf.jsqlparser.expression.Expression;
import org.apache.hadoop.hbase.KeyValue;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: gzy
 * Date: 13-11-21
 * Time: 下午4:33
 * To change this template use File | Settings | File Templates.
 */
public interface ExeSql {
    public void init( String sql);
    public boolean setColumnValue(String ColumnName,byte[] ColumnValue);
    public String getColumnname(byte[] Family,byte[] Qualifier);
    public boolean getrowkeyhave(byte[] rowkey);
    public boolean exe();
}
