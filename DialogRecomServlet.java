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

@WebServlet(name = "DialogRecomServlet", urlPatterns = {"/dialogRecom"})
public class DialogRecomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MySQLConnection connection = new MySQLConnection();

        String exe = "python";
        // FIXME
        String command = "D:\\EE542HW\\final\\pythonCode\\rec_list_display.py";
        String userId = "123456";
        ArrayList<String> messages = connection.getMessages(userId);
        StringBuilder stringBuilder = new StringBuilder();
        for(String str:messages){
            stringBuilder.append(str+";");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);

        String[] cmdArr = new String[] {exe,command,stringBuilder.toString()};
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
