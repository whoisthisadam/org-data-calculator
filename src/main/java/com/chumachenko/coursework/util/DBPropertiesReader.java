package com.chumachenko.coursework.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBPropertiesReader {


    public static final String POSTRGES_DRIVER_NAME = "POSTRGES_DRIVER_NAME";
    public static final String DATABASE_URL = "DATABASE_URL";
    public static final String DATABASE_PORT = "DATABASE_PORT";
    public static final String DATABASE_NAME = "DATABASE_NAME";
    public static final String DATABASE_LOGIN = "DATABASE_LOGIN";
    public static final String DATABASE_PASSWORD = "DATABASE_PASSWORD";

    public static Connection getConnection() throws SQLException {
        try {
            String driver = DBPropertiesReader.getProperty(DBPropertiesReader.POSTRGES_DRIVER_NAME);

            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        String url = DBPropertiesReader.getProperty(DBPropertiesReader.DATABASE_URL);
        String port = DBPropertiesReader.getProperty(DBPropertiesReader.DATABASE_PORT);
        String dbName = DBPropertiesReader.getProperty(DBPropertiesReader.DATABASE_NAME);
        String login = DBPropertiesReader.getProperty(DBPropertiesReader.DATABASE_LOGIN);
        String password = DBPropertiesReader.getProperty(DBPropertiesReader.DATABASE_PASSWORD);

        String jdbcURL = StringUtils.join(url, port, dbName);

        return DriverManager.getConnection(jdbcURL, login, password);
    }

    public static String getProperty(String key) {
        return ResourceBundle.getBundle("database").getString(key);
    }

}
