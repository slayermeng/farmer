package org.farmer.parser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.drop.Drop;
import org.farmer.transfer.SqlObjectToHBase;

import java.io.StringReader;
import java.util.List;

import org.farmer.transfer.Plan;
import org.farmer.transfer.QueryPlan;
/**
 * User: mengxin
 * Date: 13-10-9
 * Time: 上午11:25
 */
public class CommanderDispatcher {
    CCJSqlParserManager parserManager = new CCJSqlParserManager();

    public void executeStatement(String statement) throws Exception{
        try{
        Statement stmt = parserManager.parse(new StringReader(statement));
        if(stmt instanceof Select){//查询类命令
            Select select = (Select)stmt;
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

            Plan query = new QueryPlan(plainSelect);
            List<Object> result = query.plan();
            for(int i=0;i<result.size();i++){
                if(result.get(i) instanceof String){
                    System.out.println("结果是:"+result.get(i).toString());
                }else if(result.get(i) instanceof Double){
                    System.out.println("结果是:"+(Double)result.get(i));
                }

            }
        }else if(stmt instanceof Delete){
            Delete delete = (Delete)stmt;//数据操作类命令
            System.out.println(delete.getTable());
        }else if(stmt instanceof Insert){
            Insert insert = (Insert)stmt;//数据操作类命令
            System.out.println(insert.getTable());
        }else if(stmt instanceof Update){
            Update update = (Update)stmt;//数据操作类命令
        }else if(stmt instanceof Drop){//表修改
            Drop drop = (Drop)stmt;
            throw new Exception("Statement not supported");
        }else if(stmt instanceof CreateTable){//表修改
            CreateTable ct = (CreateTable)stmt;
            throw new Exception("Statement not supported");
        }else{
            throw new Exception("grammer error!");
        }
        }catch(JSQLParserException e){
            e.printStackTrace();
        }
    }
}
