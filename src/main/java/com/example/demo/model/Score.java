package com.example.demo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Score implements Comparable<Score> {

    private int score;
    private String timestamp;
    private String difficulty;

    public Score(int score, String difficulty) {
        this.score = score;
        this.difficulty = difficulty;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Score(int score, String difficulty, String timestamp) {
        this.score = score;
        this.difficulty = difficulty;
        this.timestamp = timestamp;
    }

    public int getScore() {
        return score;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public int compareTo(Score other) {
        return Integer.compare(other.score, this.score);
    }

    @Override
    public String toString() {
        return score + "分 [" + difficulty + "] " + timestamp;
    }
}
