public class User {
    private int score;

    // takes care of highscores related to user
    public User() {
        this.score = 0;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addToScore(int score) {
        this.score += score;
    }
}
