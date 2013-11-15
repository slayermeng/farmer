package org.farmer.query;

import java.util.LinkedList;
import org.farmer.transfer.ObjectExpression;
import org.farmer.transfer.SQLParserUtil;

import net.sf.jsqlparser.parser.CCJSqlParserManager;

public class TestParser {
    public static void main(String[] args) throws Exception {
        SQLParserUtil parser = new SQLParserUtil();
        String sql = "SELECT * FROM D_FILE13 WHERE" +
                "" +
                " keyword is not null" +
                "" +
                " or" +
                " abc>12 " +
                "and " +
                "(D_FILE13.KEYWORD >= '12' AND D_FILE13.HH = '122' AND ((D_FILE13.FLH > '1' OR D_FILE13.GDFS < 2 ) OR D_FILE13.MJ > '秘密') AND 1=1)  ";
		String sql1 = "SELECT A FROM TAB WHERE B IN (expr1,expr2,expr3)";
//        String sql = "select * from test where name = 'mengxin' and age=30 or sex=1 and c=1 or d=2 and c=3 and d=1";
        LinkedList linkList = parser.getLevelObjectByExpression(sql);

        printFor(linkList);

    }

    private static void printFor(LinkedList linkList){
        for (Object object : linkList) {
            if(object instanceof LinkedList){
                System.out.println("(");
                printFor((LinkedList)object);
                System.out.println(")");
            }
            if(object instanceof String){
                System.out.println(object);
            }
            if(object instanceof ObjectExpression){
                ObjectExpression ob = (ObjectExpression)object;
                System.out.println(ob.getColumnname()+" "+ob.getExp()+" "+ob.getValue());
            }
//			System.out.println(object);
        }
    }

}
