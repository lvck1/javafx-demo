package com.example.demo.util;

import com.example.demo.model.Score;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager {

    private static final String SCORE_FILE = "scores.json";
    private static final int MAX_SCORES = 10;
    private List<Score> scores;
    private Gson gson;

    public ScoreManager() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        scores = loadScores();
    }

    public void addScore(int scoreValue, String difficulty) {
        Score score = new Score(scoreValue, difficulty);
        scores.add(score);
        Collections.sort(scores);
        if (scores.size() > MAX_SCORES) {
            scores = new ArrayList<>(scores.subList(0, MAX_SCORES));
        }
        saveScores();
    }

    public List<Score> getTopScores() {
        return new ArrayList<>(scores);
    }

    public Score getHighScore() {
        return scores.isEmpty() ? null : scores.get(0);
    }

    public void clearScores() {
        scores.clear();
        saveScores();
    }

    private List<Score> loadScores() {
        Path path = Paths.get(SCORE_FILE);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(SCORE_FILE)) {
            Type listType = new TypeToken<ArrayList<Score>>() {}.getType();
            List<Score> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("加载积分榜失败: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveScores() {
        try (Writer writer = new FileWriter(SCORE_FILE)) {
            gson.toJson(scores, writer);
        } catch (IOException e) {
            System.err.println("保存积分榜失败: " + e.getMessage());
        }
    }
}
