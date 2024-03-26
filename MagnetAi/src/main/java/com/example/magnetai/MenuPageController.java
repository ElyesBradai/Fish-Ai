package com.example.magnetai;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuPageController {
    public Button startmenu;
    public Button helpmenu;
    public Button exitmenu;

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

        exitmenu.setOnAction(actionEvent -> {});

    }
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    public void showhelppage(){
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

        FXMLLoader fxmlloader = new FXMLLoader(MainClass.class.getResource("FxUI.fxml"));
        Scene scene = new Scene(fxmlloader.load(), 1300, 1300);
        stage.setTitle("Hello");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

    }

}

