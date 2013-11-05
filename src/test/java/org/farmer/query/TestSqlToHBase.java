package org.farmer.query;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.farmer.parser.CommanderDispatcher;
import org.junit.Test;

import java.io.StringReader;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-23
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class TestSqlToHBase {
    CommanderDispatcher parser = new CommanderDispatcher();

    @Test
    public void execute() throws Exception{
        parser.executeStatement("select count(q1) from testtable where q1=mengxin and q2=30");
    }

    @Test
    public void parser() throws Exception{
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select)parserManager.parse(new StringReader("select * from abc where a in (1,2,3)"));
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        Expression ex = plainSelect.getWhere();
        System.out.println(ex.toString());
    }
}
