package com.example.demo.controller;

import com.example.demo.App;
import com.example.demo.model.Snake;
import com.example.demo.service.GameEngine;
import com.example.demo.util.ScoreManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Canvas gameCanvas;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label difficultyLabel;

    private GameEngine gameEngine;
    private ScoreManager scoreManager;
    private GameEngine.Difficulty currentDifficulty;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scoreManager = new ScoreManager();
        currentDifficulty = loadDifficulty();
        initGame();
        setupKeyHandler();
    }

    private void initGame() {
        double width = gameCanvas.getWidth();
        double height = gameCanvas.getHeight();
        
        if (width <= 0 || height <= 0) {
            width = 600;
            height = 400;
        }
        
        gameEngine = new GameEngine(
            (int) width,
            (int) height,
            currentDifficulty
        );

        difficultyLabel.setText("难度: " + currentDifficulty.getName());

        gameEngine.setScoreChangedCallback(score -> {
            scoreLabel.setText("分数: " + score);
        });

        gameEngine.setGameOverCallback(score -> {
            scoreManager.addScore(score, currentDifficulty.getName());
            showGameOverDialog(score);
        });

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gameEngine.start(gc);
    }

    private void setupKeyHandler() {
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPressed);
    }

    private void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.P) {
            gameEngine.togglePause();
            gameCanvas.requestFocus();
            return;
        }

        if (code == KeyCode.G) {
            gameEngine.toggleAutoMode();
            return;
        }

        if (gameEngine.isGameOver() || gameEngine.isPaused()) {
            return;
        }

        switch (code) {
            case UP, W -> gameEngine.setSnakeDirection(Snake.Direction.UP);
            case DOWN, S -> gameEngine.setSnakeDirection(Snake.Direction.DOWN);
            case LEFT, A -> gameEngine.setSnakeDirection(Snake.Direction.LEFT);
            case RIGHT, D -> gameEngine.setSnakeDirection(Snake.Direction.RIGHT);
        }
        
        updateScoreLabel();
    }
    
    private void updateScoreLabel() {
        scoreLabel.setText("分数: " + gameEngine.getScore());
    }

    @FXML
    private void handlePause() {
        gameEngine.togglePause();
    }

    @FXML
    private void handleToggleAutoMode() {
        gameEngine.toggleAutoMode();
    }

    @FXML
    private void handleRestart() {
        gameEngine.stop();
        initGame();
    }

    @FXML
    private void handleBackToMenu() {
        gameEngine.stop();
        try {
            App.switchScene("/fxml/mainmenu.fxml");
        } catch (Exception e) {
            showError("无法返回菜单: " + e.getMessage());
        }
    }

    private void showGameOverDialog(int score) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("游戏结束");
        alert.setHeaderText("游戏结束！");
        alert.setContentText("你的得分: " + score + "\n分数已保存到积分榜！");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                handleBackToMenu();
            }
        });
    }

    private GameEngine.Difficulty loadDifficulty() {
        String savedDifficulty = System.getProperty("game.difficulty", "EASY");
        return GameEngine.Difficulty.valueOf(savedDifficulty);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("错误");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
