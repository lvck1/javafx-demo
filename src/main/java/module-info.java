module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    
    opens com.example.demo to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml;
}