package com.project.movie.db;

import com.project.movie.entity.Movie;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class MySQLConnection {
    private Connection conn;

    public MySQLConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(MySQLUtil.URL);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        if(conn != null){
            try{
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void saveMovie(Movie movie){
        if(conn == null){
            System.err.println("DB connection failed");
            return;
        }
        String insertMovieSql = "INSERT IGNORE INTO movies VALUES (?,?,?,?,?)";
        try{
            PreparedStatement statement = conn.prepareStatement(insertMovieSql);
            statement.setString(1,movie.getId());
            statement.setString(2,movie.getTitle());
            statement.setString(3,movie.getGenres());
            statement.setString(4,movie.getMovieLogo());
            statement.setString(5,movie.getUrl());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setFavoriteMovies(String userId, Movie movie){
        if(conn == null){
            if(conn == null){
                System.err.println("DB connection failed");
                return;
            }
            saveMovie(movie);
            String sql = "INSERT IGNORE INTO history (user_id, movie_id) VALUES(?,?)";
            try{
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1,userId);
                statement.setString(2,movie.getId());
                statement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void unsetFavoriteMovies(String userId, String movieId) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return;
        }
        String sql = "DELETE FROM history WHERE user_id = ? AND movie_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, movieId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getFavoriteMovieIds(String userId){
        if(conn == null){
            System.err.println("DB connection failed");
            return new HashSet<>();
        }

        Set<String> favoriteMovies = new HashSet<>();

        try{
            String sql = "SELECT movie_id FROM history WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,userId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                String movieId = rs.getString("movie_id");
                favoriteMovies.add(movieId);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return favoriteMovies;
    }

    public Set<Movie> getFavoriteMovies(String userId){
        if(conn == null){
            System.err.println("DB connection failed");
            return new HashSet<>();
        }
        Set<Movie> favoriteMovies = new HashSet<>();
        Set<String> favoriteMovieIds = getFavoriteMovieIds(userId);

        String sql = "SELECT * FROM movies WHERE movie_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            for (String movieId : favoriteMovieIds) {
                statement.setString(1, movieId);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    favoriteMovies.add(new Movie(rs.getString("movie_id")
                            ,rs.getString("title")
                            ,rs.getString("genres")
                            ,rs.getString("MovieLogo")
                            ,rs.getString("url")
                            ,true));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favoriteMovies;
    }

    public String getFullname(String userId){
        if(conn == null){
            System.err.println("DB connection failed");
            return "";
        }
        String name = "";
        String sql = "SELECT first_name, last_name FROM users WHERE user_id = ?";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,userId);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                name = rs.getString("first_name") + " " + rs.getString("last_name");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return name;
    }

    public boolean verifyLogin(String userId, String password){
        if(conn == null){
            System.err.println("DB connection failed");
            return false;
        }
        String sql = "SELECT user_id FROM users WHERE user_id = ? AND password = ?";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,userId);
            statement.setString(2,password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                return true;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean addUser(String userId, String password, String firstname, String lastname){
        if (conn == null){
            System.err.println("DB connection failed");
            return false;
        }
        String sql = "INSERT IGNORE INTO users VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,userId);
            statement.setString(2, password);
            statement.setString(3, firstname);
            statement.setString(4, lastname);

            return statement.executeUpdate() == 1; //被改变
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
