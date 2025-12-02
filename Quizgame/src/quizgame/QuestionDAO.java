package quizgame;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public List<Question> getRandomQuestions() throws Exception {

        Connection con = DBConnection.connect();
        String sql = "SELECT * FROM questions ORDER BY RAND() LIMIT 5";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Question> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Question(
                rs.getString("question"),
                new String[]{
                    rs.getString("option1"),
                    rs.getString("option2"),
                    rs.getString("option3"),
                    rs.getString("option4")
                },
                rs.getInt("answer")
            ));
        }

        return list;
    }
}
