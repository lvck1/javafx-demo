package com.example.demo.controller;

import com.example.demo.App;
import com.example.demo.model.Score;
import com.example.demo.util.ScoreManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ScoreboardController implements Initializable {

    @FXML
    private ListView<String> scoreList;

    private ScoreManager scoreManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scoreManager = new ScoreManager();
        loadScores();
    }

    private void loadScores() {
        List<Score> scores = scoreManager.getTopScores();
        ObservableList<String> scoreStrings = FXCollections.observableArrayList();

        if (scores.isEmpty()) {
            scoreStrings.add("暂无积分记录");
        } else {
            for (int i = 0; i < scores.size(); i++) {
                Score score = scores.get(i);
                scoreStrings.add((i + 1) + ". " + score.getScore() + "分 [" + score.getDifficulty() + "] " + score.getTimestamp());
            }
        }

        scoreList.setItems(scoreStrings);
    }

    @FXML
    private void handleClear(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("清空积分榜");
        alert.setHeaderText("确认清空");
        alert.setContentText("确定要清空所有积分记录吗？此操作不可恢复！");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            scoreManager.clearScores();
            loadScores();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            App.switchScene("/fxml/mainmenu.fxml");
        } catch (Exception e) {
            showError("无法返回菜单: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("错误");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
