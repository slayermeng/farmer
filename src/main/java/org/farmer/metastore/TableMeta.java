package org.farmer.metastore;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-28
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
public class TableMeta {
    private String tableName;

    private String[] columnFamily;

    private String[] column;

    public String getTableName(){
        return tableName;
    }

    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public String[] getColumnFamily(){
        return columnFamily;
    }

    public String[] getColumn(){
        return column;
    }

    public void setColumn(String[] column) {
        this.column = column;
    }

    public void setColumnFamily(String[] columnFamily) {
        this.columnFamily = columnFamily;
    }
}
