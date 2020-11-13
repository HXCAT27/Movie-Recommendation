package com.project.movie.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.movie.db.MySQLConnection;
import com.project.movie.entity.HistoryRequestBody;
import com.project.movie.entity.Movie;
import com.project.movie.entity.ResultResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebServlet(name = "HistoryServlet", urlPatterns = {"/history"})
public class HistoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(403);
            mapper.writeValue(response.getWriter(), new ResultResponse("Session Invalid"));
            return;
        }

        response.setContentType("application/json");
        HistoryRequestBody body = mapper.readValue(request.getReader(), HistoryRequestBody.class);

        MySQLConnection connection = new MySQLConnection();
        connection.setFavoriteMovies(body.userId, body.favorite);
        connection.close();

        ResultResponse resultResponse = new ResultResponse("SUCCESS");
        mapper.writeValue(response.getWriter(), resultResponse);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(403);
            mapper.writeValue(response.getWriter(), new ResultResponse("Session Invalid"));
            return;
        }

        response.setContentType("application/json");

        String userId = request.getParameter("user_id");

        MySQLConnection connection = new MySQLConnection();
        Set<Movie> movies = connection.getFavoriteMovies(userId);
        connection.close();
        mapper.writeValue(response.getWriter(), movies);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(403);
            mapper.writeValue(response.getWriter(), new ResultResponse("Session Invalid"));
            return;
        }

        response.setContentType("application/json");

        HistoryRequestBody body = mapper.readValue(request.getReader(), HistoryRequestBody.class);

        MySQLConnection connection = new MySQLConnection();
        connection.unsetFavoriteMovies(body.userId, body.favorite.getId());
        connection.close();

        ResultResponse resultResponse = new ResultResponse("SUCCESS");
        mapper.writeValue(response.getWriter(), resultResponse);
    }
}
