package quizgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML private Label questionLabel, timerLabel, scoreLabel;
    @FXML private RadioButton opt1, opt2, opt3, opt4;
    @FXML private Button nextButton, exitButton, startButton;
    @FXML private TextField nameField;

    private ToggleGroup group = new ToggleGroup();
    private List<Question> questions;

    private int index = 0;
    private int score = 0;
    private int time = 20;

    private Timeline timer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opt1.setToggleGroup(group);
        opt2.setToggleGroup(group);
        opt3.setToggleGroup(group);
        opt4.setToggleGroup(group);
    }

    @FXML
    private void startGame() throws Exception {
        questions = new QuestionDAO().getRandomQuestions();
        index = 0;
        score = 0;
        scoreLabel.setText("Score: 0");

        loadQuestion();
        startTimer();
    }

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            time--;
            timerLabel.setText("Time: " + time);

            if (time == 0) nextQuestion();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void loadQuestion() {
        Question q = questions.get(index);

        questionLabel.setText(q.getQuestion());
        opt1.setText(q.getOptions()[0]);
        opt2.setText(q.getOptions()[1]);
        opt3.setText(q.getOptions()[2]);
        opt4.setText(q.getOptions()[3]);

        group.selectToggle(null);
        time = 20;
    }

    @FXML
    private void nextQuestion() {
        checkAnswer();
        index++;

        if (index < questions.size()) {
            loadQuestion();
        } else {
            finishGame();
        }
    }

    private void checkAnswer() {
        int selected = -1;

        if (opt1.isSelected()) selected = 1;
        else if (opt2.isSelected()) selected = 2;
        else if (opt3.isSelected()) selected = 3;
        else if (opt4.isSelected()) selected = 4;

        if (selected == questions.get(index).getAnswer())
            score++;

        scoreLabel.setText("Score: " + score);
    }

    private void finishGame() {
        timer.stop();

        try {
            new ScoreDAO().saveScore(nameField.getText(), score);
        } catch (Exception e) {
            e.printStackTrace();
        }

        questionLabel.setText("Game Over! Final Score: " + score);
    }

    @FXML
    private void exitGame() {
        Platform.exit();
    }
}
