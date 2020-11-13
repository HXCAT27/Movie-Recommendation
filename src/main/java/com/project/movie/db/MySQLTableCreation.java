package com.project.movie.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MySQLTableCreation {
    public static void main(String[] args){
        try{
            // Step 1 Connect to MySQL
            System.out.println("Connecting to "+MySQLUtil.URL);
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(MySQLUtil.URL);
            if(conn == null){
                return;
            }

            // Step 2 Drop tables in case they exist
            Statement statement = conn.createStatement();

            String sql = "DROP TABLE IF EXISTS history";
            statement.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS movies";
            statement.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(sql);

            // Step 3 Create new tables
            sql = "CREATE TABLE users ("
                    + "user_id VARCHAR(255) NOT NULL,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "first_name VARCHAR(255),"
                    + "last_name VARCHAR(255),"
                    + "PRIMARY KEY (user_id)"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE movies ("
                    + "movie_id VARCHAR(255) NOT NULL,"
                    + "title VARCHAR(255),"
                    + "genres VARCHAR(255),"
                    + "MovieLogo VARCHAR(255),"
                    + "url VARCHAR(255),"
                    + "PRIMARY KEY (movie_id)"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE history ("
                    + "user_id VARCHAR(255) NOT NULL,"
                    + "movie_id VARCHAR(255) NOT NULL,"
                    + "last_favor_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "PRIMARY KEY (user_id, movie_id),"
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id),"
                    + "FOREIGN KEY (movie_id) REFERENCES movies(movie_id)"
                    + ")";
            statement.executeUpdate(sql);
            // Step 4 insert fake user 1111/3229c1097c00d497a0fd282d586be050
            sql = "INSERT INTO users VALUES('1111', '3229c1097c00d497a0fd282d586be050', 'Ria', 'Zhao')";
            statement.executeUpdate(sql);

            conn.close();
            System.out.println("Import done successfully");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
