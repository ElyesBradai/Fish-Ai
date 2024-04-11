package com.example.magnetai;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuPageController {
    public Button startmenu;
    public Button helpmenu;
    public Button exitmenu;
    @FXML
    private Label welcomeText;

    @FXML
    public void initialize() {

        helpmenu.setOnAction(actionEvent -> showhelppage());

        startmenu.setOnAction(actionEvent -> {
            try {
                showstartmenu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        exitmenu.setOnAction(actionEvent -> {
            System.exit(0);
        });

    }

    public void showhelppage() {
        Stage helppagestage = new Stage();
        Text text = new Text(30, 100, "Information");
        VBox vbox = new VBox(text);
        Scene scene = new Scene(vbox, 800, 300);
        helppagestage.setTitle("Information Page");
        helppagestage.setScene(scene);
        helppagestage.show();
    }

    public void showstartmenu() throws IOException {
        Stage stage = new Stage();


        FXMLLoader fxmlloader = new FXMLLoader(MainClass.class.getResource("oldFX.fxml"));
        Scene scene = new Scene(fxmlloader.load(), 1300, 1000);


        // Add a listener to the scene's size property
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            // Handle width change, resize UI elements if needed
            double newWidth = newVal.doubleValue();
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            // Handle height change, resize UI elements if needed
            double newHeight = newVal.doubleValue();
        });


        stage.setTitle("Hello");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

}

