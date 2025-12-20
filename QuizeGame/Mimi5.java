import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Mimi5 extends JFrame {
    private Connection conn;
    private ArrayList<Question> questions;
    private int currentIndex = 0;
    private int score = 5;
    private int wins = 0;
    private int losses = 0;

    private JLabel questionLabel, scoreLabel;
    private JButton[] optionButtons = new JButton[4];
    private JComboBox<String> levelCombo;
    private JButton startButton;

    private String currentLevel = "Easy";
    private boolean mediumUnlocked = false;
    private boolean hardUnlocked = false;
    private boolean rulesShown = false;

    private String playerName; // ✅ Player name
    private Color defaultButtonColor = new Color(135, 206, 250);

    public Mimi5() {
        // Player name input
        playerName = JOptionPane.showInputDialog(this, "Enter your name:", "Player Name", JOptionPane.PLAIN_MESSAGE);
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player";
        }

        setTitle("🎯 Quiz Game - " + playerName);
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Background gradient
        JPanel bgPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(58, 123, 213),
                        0, getHeight(), new Color(58, 96, 115));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(new BorderLayout());
        add(bgPanel);

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        topPanel.setOpaque(false);

        levelCombo = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        levelCombo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        levelCombo.setForeground(Color.BLACK);

        startButton = createStyledButton("🚀 Start Game", new Color(60, 179, 113));
        scoreLabel = new JLabel(playerName + " Score: " + score);
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        scoreLabel.setForeground(Color.YELLOW);

        topPanel.add(new JLabel("Select Level: "));
        topPanel.add(levelCombo);
        topPanel.add(startButton);
        topPanel.add(scoreLabel);

        bgPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        questionLabel = new JLabel("<html><center>Question will appear here</center></html>");
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setPreferredSize(new Dimension(650, 120));
        questionLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        centerPanel.add(questionLabel);

        bgPanel.add(centerPanel, BorderLayout.CENTER);

        // Options
        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        optionsPanel.setOpaque(false);

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = createStyledButton("Option " + (i + 1), defaultButtonColor);
            optionButtons[i].setEnabled(false);
            optionsPanel.add(optionButtons[i]);
        }

        bgPanel.add(optionsPanel, BorderLayout.SOUTH);

        // Database connection
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Quiz_game", "root", "mimi@170!M");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }

        // Button actions
        startButton.addActionListener(e -> {
            if (!rulesShown) {
                showRulesPage();
                rulesShown = true;
            } else {
                startGame();
            }
        });

        for (int i = 0; i < 4; i++) {
            int idx = i;
            optionButtons[i].addActionListener(e -> checkAnswer(idx + 1));
        }

        updateLevelAccess();
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(baseColor);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void showRulesPage() {
        JFrame rulesFrame = new JFrame("📜 Game Rules");
        rulesFrame.setSize(500, 400);
        rulesFrame.setLocationRelativeTo(this);
        rulesFrame.setLayout(new BorderLayout());

        JTextArea rulesText = new JTextArea(
                """
                🎮 Welcome to the Quiz Game!

                🧾 Rules:
                1️⃣ Start with 5 points.
                2️⃣ Each correct answer adds +10 points.
                3️⃣ Each wrong answer deducts -2 points.
                4️⃣ Reach 50 points to win the level.
                5️⃣ Score 0 → Game Over!
                6️⃣ Unlock next level by winning.

                👉 Choose wisely and have fun!
                """
        );
        rulesText.setEditable(false);
        rulesText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rulesText.setMargin(new Insets(15, 15, 15, 15));

        JPanel buttonPanel = new JPanel();
        JButton yesBtn = new JButton("✅ Yes, I Agree");
        JButton noBtn = new JButton("❌ No, Exit");

        yesBtn.setBackground(new Color(60, 179, 113));
        noBtn.setBackground(new Color(255, 99, 71));

        yesBtn.addActionListener(e -> {
            rulesFrame.dispose();
            startGame();
        });
        noBtn.addActionListener(e -> System.exit(0));

        buttonPanel.add(yesBtn);
        buttonPanel.add(noBtn);

        rulesFrame.add(new JScrollPane(rulesText), BorderLayout.CENTER);
        rulesFrame.add(buttonPanel, BorderLayout.SOUTH);
        rulesFrame.setVisible(true);
    }

    private void updateLevelAccess() {
        levelCombo.removeAllItems();
        levelCombo.addItem("Easy");
        if (mediumUnlocked) levelCombo.addItem("Medium");
        if (hardUnlocked) levelCombo.addItem("Hard");
    }

    private void startGame() {
        currentLevel = (String) levelCombo.getSelectedItem();

        int questionCount = switch (currentLevel) {
            case "Medium" -> 12;
            case "Hard" -> 15;
            default -> 10;
        };

        questions = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM questions WHERE level=? ORDER BY RAND() LIMIT ?");
            ps.setString(1, currentLevel);
            ps.setInt(2, questionCount);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                questions.add(new Question(
                        rs.getString("question"),
                        new String[]{rs.getString("option1"), rs.getString("option2"),
                                rs.getString("option3"), rs.getString("option4")},
                        rs.getInt("answer")
                ));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching questions: " + ex.getMessage());
        }

        score = 5;
        scoreLabel.setText(playerName + " Score: " + score);
        currentIndex = 0;
        startButton.setEnabled(false);
        levelCombo.setEnabled(false);
        for (JButton btn : optionButtons) {
            btn.setEnabled(true);
            btn.setBackground(defaultButtonColor);
        }

        displayQuestion();
    }

    private void displayQuestion() {
        if (currentIndex >= questions.size()) {
            endGame();
            return;
        }
        Question q = questions.get(currentIndex);
        questionLabel.setText("<html><div style='width:620px; text-align:center;'>" + q.getQuestion() + "</div></html>");
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(q.getOptions()[i]);
            optionButtons[i].setBackground(defaultButtonColor);
            optionButtons[i].setEnabled(true);
        }
    }

    private void checkAnswer(int selected) {
        Question q = questions.get(currentIndex);
        int correct = q.getAnswer();

        if (selected == correct) {
            optionButtons[selected - 1].setBackground(Color.GREEN);
            score += 10;
            JOptionPane.showMessageDialog(this, "✅ Correct! +10 points");
        } else {
            optionButtons[selected - 1].setBackground(Color.RED);
            optionButtons[correct - 1].setBackground(Color.GREEN);
            score -= 2;
            JOptionPane.showMessageDialog(this, "❌ Wrong! -2 points\nCorrect: " + q.getOptions()[correct - 1]);
        }

        scoreLabel.setText(playerName + " Score: " + score);

        for (JButton btn : optionButtons) btn.setEnabled(false);

        new javax.swing.Timer(1000, e -> {
            ((javax.swing.Timer) e.getSource()).stop();
            if (score >= 50) {
                wins++;
                JOptionPane.showMessageDialog(this, "🏆 " + playerName + " WON the " + currentLevel + " level!\nScore: " + score + "\nWins: " + wins + " Losses: " + losses);
                unlockNextLevel();
                resetGame();
                return;
            }
            if (score <= 0) {
                losses++;
                JOptionPane.showMessageDialog(this, "💀 " + playerName + " LOST! Try again.\nScore: " + score + "\nWins: " + wins + " Losses: " + losses);
                resetGame();
                return;
            }
            currentIndex++;
            displayQuestion();
        }).start();
    }

    private void unlockNextLevel() {
        if (currentLevel.equals("Easy")) {
            mediumUnlocked = true;
            JOptionPane.showMessageDialog(this, "🔓 Medium level unlocked!");
        } else if (currentLevel.equals("Medium")) {
            hardUnlocked = true;
            JOptionPane.showMessageDialog(this, "🔓 Hard level unlocked!");
        } else if (currentLevel.equals("Hard")) {
            JOptionPane.showMessageDialog(this, "🎉 " + playerName + " completed all levels!");
        }
        updateLevelAccess();
    }

    private void endGame() {
        JOptionPane.showMessageDialog(this, "🏁 Level Over! " + playerName + " Final Score: " + score + "\nWins: " + wins + " Losses: " + losses);
        resetGame();
    }

    private void resetGame() {
        startButton.setEnabled(true);
        levelCombo.setEnabled(true);
        for (JButton btn : optionButtons) btn.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Mimi5 game = new Mimi5();
            game.setVisible(true);
        });
    }
}
