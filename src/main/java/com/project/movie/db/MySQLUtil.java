package com.project.movie.db;

public class MySQLUtil {
    private static final String INSTANCE = "movierecommendation.cc8igxwh6gmd.us-west-1.rds.amazonaws.com";
    private static final String PORT_NUM = "3306";
    public static final String DB_NAME = "movie";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "Zhao123456";
    public static final String URL = "jdbc:mysql://"
            + INSTANCE + ":" + PORT_NUM + "/" + DB_NAME
            + "?user=" + USERNAME + "&password=" + PASSWORD
            + "&autoReconnect=true&serverTimezone=UTC";
}
