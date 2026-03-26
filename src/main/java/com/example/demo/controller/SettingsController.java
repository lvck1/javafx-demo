package com.example.demo.controller;

import com.example.demo.App;
import com.example.demo.service.GameEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private RadioButton easyRadio;
    @FXML
    private RadioButton mediumRadio;
    @FXML
    private RadioButton hardRadio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCurrentSettings();
    }

    private void loadCurrentSettings() {
        String savedDifficulty = System.getProperty("game.difficulty", "EASY");
        switch (savedDifficulty) {
            case "EASY" -> easyRadio.setSelected(true);
            case "MEDIUM" -> mediumRadio.setSelected(true);
            case "HARD" -> hardRadio.setSelected(true);
            default -> easyRadio.setSelected(true);
        }
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String difficulty;
        if (easyRadio.isSelected()) {
            difficulty = "EASY";
        } else if (mediumRadio.isSelected()) {
            difficulty = "MEDIUM";
        } else {
            difficulty = "HARD";
        }

        System.setProperty("game.difficulty", difficulty);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("设置保存");
        alert.setHeaderText(null);
        alert.setContentText("难度设置已保存！");
        alert.showAndWait();
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
