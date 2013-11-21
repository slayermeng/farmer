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
    private byte[] Value;
    private String schema = "";
    private boolean end = false;

    public EndExpression(byte[] Value,String schema) {
        this.Value = Value;
        this.schema = schema;
    }
    public byte[] getValue() {
        return Value;
    }

    public void setValue(byte [] bytes) {
        Value = bytes;
    }
    public String getschema() {
        return schema;
    }

    public void setschema(String string) {
        schema = string;
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
    public String toString() {
        return " s:"+schema+ " v: "+ new String(Value);
    }
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        //To change body of implemented methods use File | Settings | File Templates.

    }
}
