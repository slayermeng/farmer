package org.farmer.parser;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.schema.*;
/**
 * Created with IntelliJ IDEA.
 * User: gzy
 * Date: 13-11-14
 * Time: 上午11:07
 * To change this template use File | Settings | File Templates.
 */
public class EndExpression implements  Expression {
    private byte[] columnValue;
    private String columnschema = "";
    private boolean end = false;

    public EndExpression(byte[] columnValue,String columnschema) {
        this.columnValue = columnValue;
        this.columnschema = columnschema;
    }
    public byte[] getcolumnValue() {
        return columnValue;
    }

    public void setcolumnValue(byte [] bytes) {
        columnValue = bytes;
    }
    public String getcolumnschema() {
        return columnschema;
    }

    public void setcolumnschema(String string) {
        columnschema = string;
    }
    public boolean getend() {
        return end;
    }

    public void setend(boolean bool) {
        end = bool;
    }
    public void setboolean(boolean bool) {
        end = true;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        //To change body of implemented methods use File | Settings | File Templates.

    }
}
