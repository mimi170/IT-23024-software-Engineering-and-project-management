package com.example;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/userdb","root","YOUR_PASSWORD");

            PreparedStatement pst = con.prepareStatement(
                "INSERT INTO users(name,email,password) VALUES(?,?,?)");

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, password);

            int row = pst.executeUpdate();

            PrintWriter out = response.getWriter();
            if(row > 0){
                out.println("Registration Successful!");
            } else {
                out.println("Failed!");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
