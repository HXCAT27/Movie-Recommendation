package com.project.movie.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.movie.db.MySQLConnection;
import com.project.movie.entity.Movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Set;

@WebServlet(name = "RecommendationServlet", urlPatterns = {"/recommendation"})
public class RecommendationServlet extends HttpServlet {

    public static final int TOP_K = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MySQLConnection connection = new MySQLConnection();
        /**
        Set<String> movies = connection.getFavoriteMovieIds();
        StringBuilder moviesId = new StringBuilder();
        for(String movieId:movies){
            moviesId.append(movieId).append(",");
        }
        int endIndex = moviesId.lastIndexOf(",");
        moviesId.deleteCharAt(endIndex);

         */
        String exe = "python";
        String command = "D:\\EE542HW\\final\\pythonCode\\rec_list_display.py";
        String moviesId = "1,2,3,4,5";
        String[] cmdArr = new String[] {exe,command,moviesId.toString(),String.valueOf(TOP_K)};
        try {
            Process process = Runtime.getRuntime().exec(cmdArr);
            InputStream is = process.getInputStream();
            BufferedReader dis = new BufferedReader(new InputStreamReader(is));
            String str = dis.readLine();
            String[] ids = str.split(",");
            ArrayList<Movie> recomMovies = new ArrayList<>();
            for(String id:ids){
                Movie movie = connection.getMovieById(id);
                recomMovies.add(movie);
            }
            process.waitFor();

            response.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            String movieJson = mapper.writeValueAsString(recomMovies);

            PrintWriter out = response.getWriter();
            out.print(movieJson);
            out.close();

            connection.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
