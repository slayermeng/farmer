package org.farmer.jdbc;

import org.junit.Before;
import org.junit.Test;

import java.lang.ClassNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import static org.junit.Assert.assertNotNull;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * User: mengxin
 * Date: 13-10-14
 * Time: 下午2:18
 */
public class JDBCTest{
    private Connection conn;

    @Before
    public void setUp(){
        try{
            Class.forName("org.farmer.jdbc.HBaseDriver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Test
    public void connect(){
        String url = "jdbc:hbase://10.10.67.34";
        String user = "";
        String password = "";
        try{
            conn = DriverManager.getConnection(url,user,password);
        }catch(SQLException e){
            e.printStackTrace();
        }
        assertNotNull(conn);
    }

    @Test
    public void createStatement(){

    }

    @Test
    public void createPreparedStatement(){
        String sql = "select * from testtable where age=?";
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,30);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("age"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
