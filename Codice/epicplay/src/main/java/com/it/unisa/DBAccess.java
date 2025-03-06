package com.it.unisa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAccess {
    private static String URL = "jdbc:mysql://localhost:3306/proj";
    private static String user = "root";
    private static String password = "root";

    public static void setURL(String url) {
        URL = url;
    }

    public static void setUser(String user) {
        DBAccess.user = user;
    }

    public static void setPassword(String password) {
        DBAccess.password = password;
    }

    public static String getConnectionURL() {
        return URL;
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, user, password);
    }
}
