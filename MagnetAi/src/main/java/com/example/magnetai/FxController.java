package com.example.magnetai;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FxController {
    public Button saveButton;

    void initalize() {

        saveButton.setOnAction(event -> {

            System.out.println("hello");

        });

    }
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}