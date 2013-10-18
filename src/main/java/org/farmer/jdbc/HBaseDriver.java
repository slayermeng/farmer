package org.farmer.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;
import java.sql.DriverPropertyInfo;
import java.util.regex.Pattern;
import java.io.IOException;
import java.util.jar.Attributes;
import java.net.URL;
import java.util.jar.Manifest;
import java.util.logging.Logger;
import java.sql.SQLFeatureNotSupportedException;

/**
 * User: mengxin
 * Date: 13-10-10
 * Time: 上午9:31
 */
public class HBaseDriver implements Driver {
    private static final Log LOG = LogFactory.getLog(HBaseDriver.class);

    static {
        try {
            DriverManager.registerDriver(new HBaseDriver());
        } catch (SQLException e) {
            LOG.error(e);
            throw new RuntimeException("Can't register jdbc driver!");
        }
    }

    /**
     * Is compliant?
     */
    private static final boolean JDBC_COMPLIANT = false;

    /**
     * jdbc url prefix
     * jdbc:hbase://localhost:8989
     */
    private static final String URL_PREFIX = "jdbc:hbase://";

    /**
     * jdbc default port
     */
    private static final String DEFAULT_PORT = "10004";

    /**
     * server host prpoerty key
     */
    private static final String HOST_PROPERTY_KEY = "HOST";

    /**
     * server port property key
     */
    private static final String PORT_PROPERTY_KEY = "PORT";

    public HBaseDriver() throws SQLException {}

    public Connection connect(String url, Properties info) throws SQLException {
        return new HBaseConnection(url, info);
    }

    /**
     * Is url a valid format?
     * @param url
     * @return
     * @throws SQLException
     */
    public boolean acceptsURL(String url) throws SQLException {
        return Pattern.matches(URL_PREFIX + ".*", url);
    }

    /**
     * jdbc driver major version
     * @return
     * manifest.mf
     */
    public int getMajorVersion() {
        int version = -1;
        try {
            String fullVersion = fetchFullVersion();
            String[] tokens = fullVersion.split("\\.");

            if (tokens != null && tokens.length > 0 && tokens[0] != null) {
                version = Integer.parseInt(tokens[0]);
            }
        } catch (Exception e) {
            version = -1;
        }
        return version;
    }

    /**
     * jdbc driver minor version
     * @return
     * manifest.mf
     */
    public int getMinorVersion() {
        int version = -1;
        try {
            String fullVersion = fetchFullVersion();
            String[] tokens = fullVersion.split("\\.");

            if (tokens != null && tokens.length > 1 && tokens[1] != null) {
                version = Integer.parseInt(tokens[1]);
            }
        } catch (Exception e) {
            version = -1;
        }
        return version;
    }

    private static Attributes manifestAttributes = null;

    private String fetchFullVersion() throws SQLException{
        return HBaseDriver.fetchManifestAttribute(Attributes.Name.IMPLEMENTATION_VERSION);
    }

    private static synchronized void loadManifestAttributes() throws IOException {
        if (manifestAttributes != null) {
            return;
        }
        Class<?> clazz = HBaseDriver.class;
        String classContainer = clazz.getProtectionDomain().getCodeSource()
                .getLocation().toString();
        URL manifestUrl = new URL("jar:" + classContainer
                + "!/META-INF/MANIFEST.MF");
        Manifest manifest = new Manifest(manifestUrl.openStream());
        manifestAttributes = manifest.getMainAttributes();
    }

    static String fetchManifestAttribute(Attributes.Name attributeName)
            throws SQLException {
        try {
            loadManifestAttributes();
        } catch (IOException e) {
            throw new SQLException("Couldn't load manifest attributes.", e);
        }
        return manifestAttributes.getValue(attributeName);
    }

    public boolean jdbcCompliant() {
        return JDBC_COMPLIANT;
    }

    private Properties parseURL(String url, Properties defaults) throws SQLException {
        Properties urlProps = (defaults != null) ? new Properties(defaults)
                : new Properties();
        if (url == null || !url.startsWith(URL_PREFIX)) {
            throw new SQLException("Invalid connection url: " + url);
        }

        if (url.length() <= URL_PREFIX.length()) {
            return urlProps;
        }

        // [hostname]:[port]
        String connectionInfo = url.substring(URL_PREFIX.length());

        // [hostname]:[port]
        String[] hostPort = connectionInfo.split("/", 2);

        // [hostname]:[port]
        if (hostPort[0].length() > 0) {
            String[] hostAndPort = hostPort[0].split(":", 2);
            urlProps.put(HOST_PROPERTY_KEY, hostAndPort[0]);
            if (hostAndPort.length > 1) {
                urlProps.put(PORT_PROPERTY_KEY, hostAndPort[1]);
            } else {
                urlProps.put(PORT_PROPERTY_KEY, DEFAULT_PORT);
            }
        }

        return urlProps;
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        if (info == null) {
            info = new Properties();
        }

        if ((url != null) && url.startsWith(URL_PREFIX)) {
            info = parseURL(url, info);
        }

        DriverPropertyInfo hostProp = new DriverPropertyInfo(HOST_PROPERTY_KEY,
                info.getProperty(HOST_PROPERTY_KEY, ""));
        hostProp.required = false;
        hostProp.description = "Hostname of HBase Server";

        DriverPropertyInfo portProp = new DriverPropertyInfo(PORT_PROPERTY_KEY,
                info.getProperty(PORT_PROPERTY_KEY, ""));
        portProp.required = false;
        portProp.description = "Port number of HBase Server";

        DriverPropertyInfo[] dpi = new DriverPropertyInfo[2];

        dpi[0] = hostProp;
        dpi[1] = portProp;

        return dpi;
    }

    /**
     * for jdk1.7
     * @return
     * @throws SQLException
     */
    public Logger getParentLogger() throws SQLException{
        throw new SQLFeatureNotSupportedException("Method not supported");
    }
}
