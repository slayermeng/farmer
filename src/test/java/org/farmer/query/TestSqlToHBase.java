package org.farmer.query;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
//import org.farmer.parser.CommanderDispatcher;
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
   // CommanderDispatcher parser = new CommanderDispatcher();
    static long a=1,b=2,c=3,d=4,e=5,f=6,g=7;
    @Test
    public void execute() throws Exception{
    //    parser.executeStatement("select count(q1) from testtable where q1=mengxin and q2=30");
    }

    @Test
    public void parser() throws Exception{
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select)parserManager.parse(new StringReader("select * from test where a=1 and b=2 and c=3 and (d=4 or e=5 )and f=7"));
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        BinaryExpression expression = (BinaryExpression)plainSelect.getWhere();
        postOrderzy(expression);
    }

    public static void postOrderzy(BinaryExpression expression){

        if(expression != null){
            if(expression.getLeftExpression() instanceof BinaryExpression){
                postOrderzy((BinaryExpression)expression.getLeftExpression());
            }
            if (expression.getRightExpression() instanceof BinaryExpression){
                postOrderzy((BinaryExpression)expression.getRightExpression());
            }
            exeNode(expression);
        }
    }

    public static boolean exeNode(BinaryExpression expression){
        System.out.println("xl:"+expression.getLeftExpression().getClass());
        System.out.println("xx:"+expression.getStringExpression() );
        System.out.println("xr:"+expression.getRightExpression());
        boolean end =false;

        if(!(expression.getLeftExpression() instanceof BinaryExpression)
                && !(expression.getRightExpression() instanceof BinaryExpression)){
        //    System.out.println("aaaaaaaaaa");
            if((expression.getLeftExpression() instanceof Column)
                    &&(expression.getRightExpression() instanceof net.sf.jsqlparser.expression.LongValue)){
        //        System.out.println("bbbbbb");
                long tmp;

                tmp = ((LongValue) expression.getRightExpression()).getValue();
                String s =((Column) expression.getLeftExpression()).getWholeColumnName();
                char[] tc = s.toCharArray();

                switch (tc[0]){
                    case 'a':  end = (tmp == a);break;
                    case 'b':  end = (tmp == b);break;
                    case 'c':  end = (tmp == c);break;
                    case 'd':  end = (tmp == d);break;
                    case 'e':  end = (tmp == e);break;
                    case 'f':  end = (tmp == f);break;
                    case 'g':  end = (tmp == g);break;
                    default: System.out.println("!!!error!!!");
                }
                System.out.println("=end: "+ end);

                if( end ){
                    expression.setNot();
                }

            }

        }
        int x=0;
        if(expression.getStringExpression() .matches( "AND")){x=1;}
        if(expression.getStringExpression() .matches( "OR")){x=2;}
        if((x > 0)&& (null==expression.getLeftExpression()&&null==expression.getLeftExpression())){
            //  if(x > 0){
                 System.out.println("ANDANDANDANDAND: "+((BinaryExpression)expression.getLeftExpression()).isNot()+" "+((BinaryExpression)expression.getRightExpression()).isNot());
            boolean tl = ((BinaryExpression)expression.getLeftExpression()).isNot();
            boolean tr = ((BinaryExpression)expression.getRightExpression()).isNot();
            if(1==x)
                end = tl & tr;
            if(2==x)
                end = tl | tr;
            if( end ){
                expression.setNot();
            }
        }

        expression.setLeftExpression(null);
        expression.setRightExpression(null);
        System.out.println("end: "+ end);
        return end;

    }



}
