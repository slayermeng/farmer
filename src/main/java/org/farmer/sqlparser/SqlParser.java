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
import org.farmer.transfer.SqlObjectToHBase;

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

    public void executeStatement(String statement) throws Exception{
        try{
        Statement stmt = parserManager.parse(new StringReader(statement));
        if(stmt instanceof Select){
            Select select = (Select)stmt;
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
            List<WithItem> item = select.getWithItemsList();

            SqlObjectToHBase soth = new SqlObjectToHBase();
            List<Object> result = soth.scan(plainSelect);
            for(int i=0;i<result.size();i++){
                if(result.get(i) instanceof String){
                    System.out.println("结果是:"+result.get(i).toString());
                }else if(result.get(i) instanceof Double){
                    System.out.println("结果是:"+(Double)result.get(i));
                }

            }
        }else if(stmt instanceof Delete){
            Delete delete = (Delete)stmt;
            System.out.println(delete.getTable());
        }else if(stmt instanceof Insert){
            Insert insert = (Insert)stmt;
            System.out.println(insert.getTable());
        }else if(stmt instanceof Update){
            Update update = (Update)stmt;
            System.out.println(update.getTable());
        }else{
            throw new Exception("grammer error!");
        }
        }catch(JSQLParserException e){
            e.printStackTrace();
        }
    }
}
