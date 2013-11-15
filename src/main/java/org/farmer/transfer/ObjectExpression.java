package org.farmer.transfer;
public class ObjectExpression {

    //字段
    private String columnname = "";
    //对比表达式 比如 <> != is null ....
    private String exp = "";
    //值
    private Object value = "";
    public String getColumnname() {
        return columnname;
    }
    public void setColumnname(String columnname) {
        this.columnname = columnname;
    }
    public String getExp() {
        return exp;
    }
    public void setExp(String exp) {
        this.exp = exp;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
}