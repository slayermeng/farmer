package org.farmer.query;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
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

    private static int level=0;

    @Test
    public void execute() throws Exception{
        parser.executeStatement("select count(q1) from testtable where q1=mengxin and q2=30");
    }

    @Test
    public void parser() throws Exception{
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select)parserManager.parse(new StringReader("select * from test where name = 'mengxin' and age=30 or sex=1 and c=1 or d=2 and c=3 and d=1"));
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        BinaryExpression expression = (BinaryExpression)plainSelect.getWhere();
        System.out.println("树深"+length(expression));
        postOrder(expression);
    }

    public static void postOrder(BinaryExpression expression){
        if(expression != null){
            if((expression.getLeftExpression() instanceof BinaryExpression)&&(expression.getRightExpression() instanceof BinaryExpression)){
                level++;
                postOrder((BinaryExpression)expression.getLeftExpression());
                //计算左子树结果
                postOrder((BinaryExpression)expression.getRightExpression());
                //计算右子树结果
                level--;
                printNode(expression);
            }

        }
    }

    public static void printNode(Expression expression){
        System.out.println(expression.toString()+"(级别"+level+")");

    }

    public static int length(BinaryExpression expression){
        int depth1;
        int depth2;
        if(expression == null) return 0;
        //左子树的深度
        if((expression.getLeftExpression()) instanceof BinaryExpression){
        depth1 = length((BinaryExpression)expression.getLeftExpression());
        //右子树的深度
        depth2 = length((BinaryExpression)expression.getRightExpression());
            if(depth1>depth2)
                return depth1+1;
            else
                return depth2+1;
        }else{
            return 1;
        }

    }

    public class Node{
        private Node parent;

        private Expression leftExpression;
        private Expression rightExpression;

        public Node(){

        }
    }
}
