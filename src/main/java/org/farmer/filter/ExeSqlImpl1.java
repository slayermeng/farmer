package org.farmer.filter;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.farmer.parser.EndExpression;

import java.io.StringReader;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: gzy
 * Date: 13-11-21
 * Time: 下午5:08
 * To change this template use File | Settings | File Templates.
 */
public class ExeSqlImpl1 implements ExeSql {

    private CCJSqlParserManager parserManager = new CCJSqlParserManager();
    private Select select;// = (Select)parserManager.parse(new StringReader("select * from test where a=1 and b=2 and c=3 and (d=4 or e=5 )and f=7"));
    private PlainSelect plainSelect;// = (PlainSelect) select.getSelectBody();
    private BinaryExpression Whereexp; //= (BinaryExpression)plainSelect.getWhere();
    private String mysql = null;
    private Map<String,EndExpression> Columnm =  new TreeMap<String,EndExpression>();
    public static final Log LOG = LogFactory.getLog(FilterTreeDemo.class);

    @Override
    public void init(String sql) {
        this.mysql = sql;
        Columnm.clear();
        try {
            select = (Select)parserManager.parse(new StringReader(mysql));
            plainSelect = (PlainSelect) select.getSelectBody();
            Whereexp = (BinaryExpression)plainSelect.getWhere();
            Traversalwhere(Whereexp);
        } catch (JSQLParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    public  void Traversalwhere(Expression expression){
        if(expression == null)
            return ;
        if(expression instanceof BinaryExpression) {
            if(((BinaryExpression)expression).getLeftExpression() != null){
                Traversalwhere(((BinaryExpression) expression).getLeftExpression());
            }
            if(((BinaryExpression)expression).getRightExpression() != null){
                Traversalwhere(((BinaryExpression) expression).getRightExpression());
            }

        }else if(expression instanceof Column){
            //     LOG.info("xxxxxxx     Column:"+expression);
            Columnm.put(((Column) expression).getColumnName(), null);
        }
        return ;
    }


    @Override
    public boolean setColumnValue(String ColumnName, byte[] ColumnValue) {
        if(Columnm.containsKey(ColumnName)){
            Columnm.put(ColumnName , new EndExpression( ColumnValue,null));
        }
       // LOG.info("fffffff     Columnm: "+Columnm.toString());

        for (String key : Columnm.keySet()) {
            LOG.info("fffffff     filterKeyValue  it: k:"+key+" v:"+Columnm.get(key));
            if(null == Columnm.get(key)){
                LOG.info("fffffff     filterKeyValue  ReturnCode: ");
                return  true ;
            }
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getColumnname(byte[] Family, byte[] Qualifier) {
        return   new String( Family)+"_"+new String(Qualifier);
    }

    @Override
    public boolean getrowkeyhave(byte[] rowkey) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean exe() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public  EndExpression getColumnvalue(Column expression){
        if(expression == null)
            return null;
        return Columnm.get(expression.getColumnName());
    }

    public  EndExpression exeNode(Expression expression){
        LOG.info("xxxxxxx     exeNode:"+expression.toString()+" class:"+expression.getClass());
        Long ll=null,rl=null;

        if(expression == null)
            return null;
        if(expression instanceof BinaryExpression) {
            LOG.info("xxxxxxx     exeNode if:"+expression.toString());

            Expression le,re;
            le = ((BinaryExpression)expression).getLeftExpression();
            re = ((BinaryExpression)expression).getRightExpression();
            if( null == le){
                ll =null;
            }else if( le instanceof Column){
                ll = Long.parseLong(new String(getColumnvalue((Column) le).getValue()));
            }else if( le instanceof LongValue){
                ll = ((LongValue) le).getValue();
            }else if( le instanceof EndExpression){
                ll =  Long.parseLong(  new String(   ((EndExpression) le).getValue()  )  );
            }
            if( null == re){
                ll =null;
            }else if( re instanceof Column){
                rl = Long.parseLong(new String(getColumnvalue((Column) re).getValue()));
            }else if( re instanceof LongValue){
                rl = ((LongValue) re).getValue();
            }else if( re instanceof EndExpression){
                rl =  Long.parseLong(new String(   ((EndExpression) re).getValue()  ));
            }
            if((null==ll)||(null==rl)){
                LOG.info("xxxxxxx     exeNode no support !!!!!!!!! "+le.getClass()+" "+re.getClass());
                return null;
            }

            Integer r;
            String es = ((BinaryExpression) expression).getStringExpression();
            if(es.equals("<=")){
                r =  ll <= rl ? 1 : 0;
            }else if(es.equals("<")){
                r =  ll < rl ? 1 : 0;
            }else if(es.equals("<>")){
                r =  ll != rl ? 1 : 0;
            }else if(es.equals("=")){
                r =  ll == rl ? 1 : 0;
            }else if(es.equals(">")){
                r =  ll > rl ? 1 : 0;
            }else if(es.equals(">=")){
                r =  ll >= rl ? 1 : 0;
            }else if(es.equals("AND")){
                if((ll>0)&&(rl>0))
                    r = 1;
                else
                    r = 0;
            }else if(es.equals("OR")){
                if((ll>0)||(rl>0))
                    r = 1;
                else
                    r = 0;
            }else{
                //  throw new RuntimeException("Unknown Compare op " + compareOp.name());
                LOG.info("xxxxxxx     exeNode no support !!!!!!!!! " + es);
                return null;
            }

            EndExpression ee = new EndExpression(r.toString().getBytes(),"endbool");
            LOG.info("xxxxxxx     exeNode result: "+ee.toString());
            return ee;
        }else{
            LOG.info("xxxxxxx     exeNode else:"+expression.toString()+" class:"+expression.getClass());
        }
        return null;
    }
    public  EndExpression exeexpression(Expression expression){
        LOG.info("xxxxxxx     exewhere:"+expression+" class:"+expression.getClass());
        Expression LeftExpression,RightExpression;
        if(expression == null)
            return null;
        if(expression instanceof BinaryExpression) {
            //     LOG.info("xxxxxxx     xl:"+((BinaryExpression)expression).getLeftExpression()+"  r: "+((BinaryExpression)expression).getLeftExpression().getClass());
            //      LOG.info("xxxxxxx     xc:"+((BinaryExpression)expression).getStringExpression()+"  c: "+((BinaryExpression)expression).getStringExpression().getClass() );
            //     LOG.info("xxxxxxx     xr:"+((BinaryExpression)expression).getRightExpression()+"  l: "+((BinaryExpression)expression).getRightExpression().getClass());
            if(((BinaryExpression)expression).getLeftExpression() instanceof BinaryExpression){
                LeftExpression = exeexpression(((BinaryExpression) expression).getLeftExpression());
                ((BinaryExpression)expression).setLeftExpression(LeftExpression);
            }
            if(((BinaryExpression)expression).getRightExpression() instanceof BinaryExpression){
                RightExpression = exeexpression(((BinaryExpression) expression).getRightExpression());
                ((BinaryExpression)expression).setRightExpression(RightExpression);
            }
            return exeNode(expression);
        }
        LOG.info("xxxxxxx     exewhere end:"+expression.toString() );
        return exeNode(expression);
    }

}
