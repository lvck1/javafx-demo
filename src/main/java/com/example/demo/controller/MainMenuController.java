package com.example.demo.controller;

import com.example.demo.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MainMenuController {

    @FXML
    private void handleStartGame(ActionEvent event) {
        try {
            App.switchScene("/fxml/game.fxml");
        } catch (Exception e) {
            showError("无法加载游戏界面: " + e.getMessage());
        }
    }

    @FXML
    private void handleScoreboard(ActionEvent event) {
        try {
            App.switchScene("/fxml/scoreboard.fxml");
        } catch (Exception e) {
            showError("无法加载积分榜: " + e.getMessage());
        }
    }

    @FXML
    private void handleSettings(ActionEvent event) {
        try {
            App.switchScene("/fxml/settings.fxml");
        } catch (Exception e) {
            showError("无法加载设置界面: " + e.getMessage());
        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("退出游戏");
        alert.setHeaderText("确认退出");
        alert.setContentText("确定要退出游戏吗？");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
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
