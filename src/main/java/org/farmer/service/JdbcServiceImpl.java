package org.farmer.service;

import org.farmer.sqlparser.SqlParser;

import java.io.StringReader;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mengxin
 * Date: 13-10-15
 * Time: 下午4:35
 * To change this template use File | Settings | File Templates.
 */
public class JdbcServiceImpl implements JdbcService.Iface{
    SqlParser parser = new SqlParser();

    public String execute(String sql){
        try{
            parser.executeStatement(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
