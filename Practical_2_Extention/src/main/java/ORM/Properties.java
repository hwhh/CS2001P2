package ORM;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Properties {

    public static java.util.Properties properties;
    public static String host, port, username, password, db;
    public static final String INPUT_FILE = "database.properties";


    public static void setup() {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    public static Map<String, String> getPropertiesForTableAutoCreation() {
        return getProperties("create");
    }

    protected static Map<String, String> getPropertiesForTableValidation() {
        return getProperties("validate");
    }

    //The properties Map contains all required parameters to build an EntityManager
    private static Map<String, String> getProperties(String hibernate_option) {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("hibernate.hbm2ddl.auto", hibernate_option);
        properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
        try {
            getDatabaseProperties(INPUT_FILE);
            properties.put("javax.persistence.jdbc.url", getURL());
            properties.put("javax.persistence.jdbc.user", username);
            properties.put("javax.persistence.jdbc.password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * extract the information from the .properties file
     * @param propertiesFile file that contains the users database properties
     * @throws IOException
     */
    public static void getDatabaseProperties(String propertiesFile) throws IOException {
        //Open properties file with automatic resources
        try (FileInputStream propInputStream = new FileInputStream(propertiesFile);) {
            //get the properties from the properties file
            properties = new java.util.Properties();
            properties.load(propInputStream);
            host = properties.getProperty("host");
            port = properties.getProperty("port");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            db = properties.getProperty("db");
            if (host == null || port == null || username == null || db == null || getURL() == null) {
                System.out.println("Bad properties file");
                System.exit(0);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getURL() {
        //Create a new string with the url of database
        StringBuilder builder = new StringBuilder();
        builder.append("jdbc:mysql://");
        builder.append(host);
        builder.append(":");
        builder.append(port);
        builder.append("/");
        builder.append(db);
        return builder.toString();
    }
}
