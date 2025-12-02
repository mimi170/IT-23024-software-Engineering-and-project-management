package quizgame;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ScoreDAO {

    public void saveScore(String name, int score) throws Exception {
        Connection con = DBConnection.connect();
        String sql = "INSERT INTO scores(player_name, score) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, name);
        ps.setInt(2, score);

        ps.executeUpdate();
    }
}
