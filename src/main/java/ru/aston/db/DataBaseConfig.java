package ru.aston.db;

public class DataBaseConfig {

    private static String url;

    private static String user;

    private static String pass;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DataBaseConfig.url = url;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        DataBaseConfig.user = user;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        DataBaseConfig.pass = pass;
    }
}
