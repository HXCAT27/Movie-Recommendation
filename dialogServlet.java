package com.project.movie.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.movie.db.MySQLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

@WebServlet(name = "DialogServlet", urlPatterns = {"/dialog"})
public class dialogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MySQLConnection connection = new MySQLConnection();
        // FIXME
        String userId = "123456";
        String message = "";

        connection.saveMessage(userId,message);
        ArrayList<String> messages = connection.getMessages(userId);

        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String movieJson = mapper.writeValueAsString(messages);

        PrintWriter out = response.getWriter();
        out.print(movieJson);
        out.close();

        connection.close();
    }
}
