import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/test";
            String user = "root";
            String pass = "mimi@170!M"; // XAMPP হলে ফাঁকা থাকে

            Connection conn = DriverManager.getConnection(url, user, pass);

            String sql = "INSERT INTO data (message) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "My name is mimi");
            ps.executeUpdate();

            conn.close();
            System.out.println("Data inserted successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
