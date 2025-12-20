import java.sql.*;
import java.util.Scanner;

public class Game {
    static String url = "jdbc:mysql://localhost:3306/quiz_game";
    static String user = "root";
    static String password = "mimi@170!M";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL driver load
            Connection conn = DriverManager.getConnection(url, user, password);

            int currentLevel = 1; // 1 = Easy, 2 = Medium, 3 = Hard

            while (currentLevel <= 3) {
                System.out.println("\nLevel " + getLevelName(currentLevel) + " Questions:");

                int score = playLevel(conn, currentLevel, sc);
                System.out.println("You scored: " + score + "/" + getTotalQuestions(currentLevel, conn));

                if (score >= getPassMark(currentLevel, conn)) {
                    System.out.println("🎉 Congratulations! You passed " + getLevelName(currentLevel) + " level.");
                    currentLevel++; // Move to next level
                } e
