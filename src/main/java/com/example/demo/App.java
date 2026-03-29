package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainmenu.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("贪吃蛇游戏");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void switchScene(String fxmlPath) throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource(fxmlPath));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(App.class.getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        System.setProperty("javafx.animation.fullscreen", "true");
        System.setProperty("prism.order", "vk,v,d3d,sw");
        launch(args);
    }
}
