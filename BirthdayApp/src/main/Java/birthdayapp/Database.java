package birthdayapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/birthdaydb";
            String user = "root";
            String pass = "";

            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
