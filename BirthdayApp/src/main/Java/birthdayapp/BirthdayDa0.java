package birthdayapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class BirthdayDAO {

    public static ObservableList<Birthday> getAll() {
        ObservableList<Birthday> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM birthdays ORDER BY date";

        try {
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(new Birthday(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("date")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void insert(String name, String date) {
        String sql = "INSERT INTO birthdays(name, date) VALUES(?, ?)";

        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, date);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(int id, String name, String date) {
        String sql = "UPDATE birthdays SET name=?, date=? WHERE id=?";

        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, date);
            ps.setInt(3, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM birthdays WHERE id=?";

        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
