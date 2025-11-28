package com.example;

import java.sql.*;

public class UserDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/userdb";
    private String jdbcUsername = "root";   // MySQL username
    private String jdbcPassword = "";       // MySQL password

    private static final String INSERT_USERS_SQL = 
        "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

    public void registerUser(User user) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
