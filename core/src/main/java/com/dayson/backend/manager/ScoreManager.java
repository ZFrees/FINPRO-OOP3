package com.Vanguard.backend.managers;

public class ScoreManager {

    private int score;
    private int highScore;

    public ScoreManager() {
        score = 0;
        highScore = 0;
    }

    public void addScore(int points) {

        if (points <= 0) return;

        score += points;

        if (score > highScore) {
            highScore = score;
        }

        System.out.println("Score: " + score);
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = Math.max(0, highScore);
    }

    public void resetScore() {
        score = 0;
    }

    public void saveHighScore() {

        if (score > highScore) {
            highScore = score;
        }
    }
}
