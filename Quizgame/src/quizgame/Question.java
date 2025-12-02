package quizgame;

public class Question {
    private String question;
    private String[] options;
    private int answer;

    public Question(String q, String[] ops, int ans) {
        question = q;
        options = ops;
        answer = ans;
    }

    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public int getAnswer() { return answer; }
}
