package quizgame;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/quizdb";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection connect() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
