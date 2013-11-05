package org.farmer.jdbc;

import org.junit.Before;
import org.junit.Test;

import java.lang.ClassNotFoundException;
import java.sql.SQLException;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;

import java.util.Enumeration;

import static org.junit.Assert.*;

/**
 * Test HBase Jdbc Driver
 */
public class TestJdbcDriver {
    private Connection conn;

    @Before
    public void setUp(){
        Class<?> drivercls = null;
        try{
            drivercls = Class.forName("org.farmer.jdbc.HBaseDriver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        assertNotNull(drivercls);
    }

    @Test
    public void testAcceptsURL() throws SQLException{
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver = null;
        while(drivers.hasMoreElements()){
            driver = drivers.nextElement();
            assertTrue(driver.acceptsURL("jdbc:hbase://"));
            assertFalse(driver.acceptsURL("jdbc:base://"));
        }
    }

    @Test
    public void testVersion(){
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver = null;
        while(drivers.hasMoreElements()){
            driver = drivers.nextElement();
            assertEquals(1,driver.getMajorVersion());
            assertEquals(2,driver.getMinorVersion());
        }
    }

    @Test
    public void testPropertyInfo() throws SQLException{
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver = null;
        while(drivers.hasMoreElements()){
            driver = drivers.nextElement();
            DriverPropertyInfo[] property = driver.getPropertyInfo("jdbc:hbase://10.10.67.34:3567", null);
            assertEquals(2,property.length);
            assertEquals("10.10.67.34",property[0].value);
            assertEquals(3567,Integer.parseInt(property[1].value));
        }
    }

    @Test
    public void connect(){
        String url = "jdbc:hbase://10.10.67.34";
        String user = "mengxin";
        String password = "1234";
        try{
            conn = DriverManager.getConnection(url,user,password);
        }catch(SQLException e){
            e.printStackTrace();
        }
        assertNotNull(conn);
    }
}
