package org.farmer.jdbc;

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

/**
 * User: mengxin
 * Date: 13-10-10
 * Time: 上午9:31
 */
public class HBaseDriver implements Driver {
    static {
        try {
            DriverManager.registerDriver(new HBaseDriver());
        } catch (SQLException e) {
            throw new RuntimeException("Can't register jdbc!");
        }
    }

    private static final String URL_PREFIX = "jdbc:hbase://";

    private static final boolean JDBC_COMPLIANT = false;

    private static final String DEFAULT_PORT = "10000";

    private static final String HOST_PROPERTY_KEY = "HOST";

    private static final String PORT_PROPERTY_KEY = "PORT";

    public HBaseDriver() throws SQLException {

    }

    public Connection connect(String url, Properties info) throws SQLException {
        return new HBaseConnection(url, info);
    }

    public boolean acceptsURL(String url) throws SQLException {
        return Pattern.matches(URL_PREFIX + ".*", url);
    }

    public int getMajorVersion() {
        int version = -1;
        try {
            String fullVersion = HBaseDriver.fetchManifestAttribute(
                    Attributes.Name.IMPLEMENTATION_VERSION);
            String[] tokens = fullVersion.split("\\."); //$NON-NLS-1$

            if (tokens != null && tokens.length > 0 && tokens[0] != null) {
                version = Integer.parseInt(tokens[0]);
            }
        } catch (Exception e) {
            // Possible reasons to end up here:
            // - Unable to read version from manifest.mf
            // - Version string is not in the proper X.x.xxx format
            version = -1;
        }
        return version;
    }

    public int getMinorVersion() {
        int version = -1;
        try {
            String fullVersion = HBaseDriver.fetchManifestAttribute(
                    Attributes.Name.IMPLEMENTATION_VERSION);
            String[] tokens = fullVersion.split("\\."); //$NON-NLS-1$

            if (tokens != null && tokens.length > 1 && tokens[1] != null) {
                version = Integer.parseInt(tokens[1]);
            }
        } catch (Exception e) {
            // Possible reasons to end up here:
            // - Unable to read version from manifest.mf
            // - Version string is not in the proper X.x.xxx format
            version = -1;
        }
        return version;
    }

    private static Attributes manifestAttributes = null;

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

        //jdbc url格式检查
        if (url == null || !url.startsWith(URL_PREFIX)) {
            throw new SQLException("Invalid connection url: " + url);
        }

        if (url.length() <= URL_PREFIX.length()) {
            return urlProps;
        }

        // [hostname]:[port]/[db_name]
        String connectionInfo = url.substring(URL_PREFIX.length());

        // [hostname]:[port] [db_name]
        String[] hostPortAndDatabase = connectionInfo.split("/", 2);

        // [hostname]:[port]
        if (hostPortAndDatabase[0].length() > 0) {
            String[] hostAndPort = hostPortAndDatabase[0].split(":", 2);
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

        DriverPropertyInfo[] dpi = new DriverPropertyInfo[3];

        dpi[0] = hostProp;
        dpi[1] = portProp;

        return dpi;
    }
}
