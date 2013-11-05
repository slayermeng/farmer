package org.farmer.transfer;

import java.util.List;

import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.statement.select.*;


/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-11-4
 * Time: 下午5:49
 * To change this template use File | Settings | File Templates.
 */
public class SelectVisitorImpl implements SelectVisitor{
    public void visit(PlainSelect plainSelect) {
        List<SelectItem> selectItems = plainSelect.getSelectItems();
    }

    public void visit(SetOperationList setOperationList) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void visit(WithItem withItem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void visit(InExpression arg0) {}
}
