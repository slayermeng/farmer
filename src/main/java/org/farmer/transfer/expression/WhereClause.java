package org.farmer.transfer.expression;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.expression.Expression;

import java.io.StringReader;

/**
 * User: mengxin
 * Date: 13-11-19
 * Time: 下午3:09
 */
public class WhereClause {
    private CCJSqlParserManager parserManager = new CCJSqlParserManager();

    private final String sql;

    public WhereClause(String sql){
        this.sql = sql;
    }

    public void parser(){
        Expression expression=null;
        try{
            Select select = (Select)parserManager.parse(new StringReader(sql));
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
            expression = plainSelect.getWhere();
        }catch(JSQLParserException e){
            e.printStackTrace();
        }
        if(expression!=null&&expression instanceof BinaryExpression){
            recursive((BinaryExpression)expression);//递归
        }
    }

    private void recursive(BinaryExpression expression){
        //后续遍历
        if(expression.getLeftExpression() instanceof BinaryExpression){
            recursive((BinaryExpression)expression.getLeftExpression());
        }
        if (expression.getRightExpression() instanceof BinaryExpression){
            recursive((BinaryExpression)expression.getRightExpression());
        }
        execExpression(expression);
    }

    private void execExpression(BinaryExpression expression){
        System.out.println("当前表达式:"+expression.toString());

    }

    public static void main(String[] args){
        WhereClause where = new WhereClause("select * from test where a=1 and b=2 or c=3 and d=4");
        where.parser();
    }
}
