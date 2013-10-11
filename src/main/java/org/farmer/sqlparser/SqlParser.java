package org.farmer.sqlparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.update.Update;

import java.io.StringReader;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-9
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
public class SqlParser {
    CCJSqlParserManager parserManager = new CCJSqlParserManager();

    public void insert(){
        String statement = "insert into testtable(name,sex,age) values ('mengxin',1,30)";
        try{
            Statement stmt = parserManager.parse(new StringReader(statement));
            Insert insert = null;
            if (stmt instanceof Insert){
                insert = (Insert)stmt;
            }
            System.out.println(insert.getTable());
        }catch(JSQLParserException e){
            System.out.println(e);
        }

    }

    public void update(){
        String statement = "update testtable set name='111' where name='123'";
        try{
            Update update = (Update)parserManager.parse(new StringReader(statement));
            System.out.println(update.getTable());
        }catch(JSQLParserException e){
            System.out.println(e);
        }

    }

    public void delete(){
        String statement = "delete from testtable where name='123'";
        try{
            Delete delete = (Delete)parserManager.parse(new StringReader(statement));
            System.out.println(delete.getTable());
        }catch(JSQLParserException e){
            System.out.println(e);
        }
    }

    public void query(){
        String statement = "select count(*) from testtable where (name='123' or name='456') and (sex=1 or sex>0) and age=19";
        try{
            Select select = (Select) parserManager.parse(new StringReader(statement));
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
            List<WithItem> item = select.getWithItemsList();
            System.out.println(plainSelect.getFromItem());

        }catch(JSQLParserException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws Exception{
       SqlParser parser = new SqlParser();
       parser.query();
    }
}
