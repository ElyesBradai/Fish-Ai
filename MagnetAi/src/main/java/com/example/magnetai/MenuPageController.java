package com.example.magnetai;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuPageController{
	public Button startmenu;
	public Button helpmenu;
	public Button exitmenu;
	@FXML
	private Label welcomeText;
	
	@FXML
	public void initialize(){
		helpmenu.setOnAction(actionEvent -> showHelpPage());
		startmenu.setOnAction(actionEvent -> {
			try {
				showstartmenu();
				((Stage) startmenu.getScene().getWindow()).close();
				}
					catch (IOException e){
					throw new RuntimeException(e);
				}
			});
		exitmenu.setOnAction(actionEvent -> {
			System.exit(0);
		});
	}

	public void showHelpPage(){
		Text text1 = new Text(30, 50, "About");
		text1.setFont(new Font("SansSerif",40));
		String text = """
                The goal of this project is to demonstrate the phenomena of magnetic fields by displaying how magnetic fields 
                are able to redirect the direction of charges. A charge (shown in red in the following visual representation) 
                will have an initial velocity.e goal of the program is to get the charge to the finish line (in yellow). We will 
                also implement superconductors (shown in blue) which are squares that are not obstacles for the charge, but for 
                the magnetic fields. This means that on blue squares, it is impossible to place a magnetic field, but the electron 
                will not be affected by them.We will also implement an Ai that chooses the location of the magnetic fields and try 
                to get the best arrangement of the two types of magnetic fields (inwards or outwards) to get the electron as close
                as possible to the finish line.
                """;
		Text text2 = new Text(30, 100, text);
		text2.setFont(new Font("SansSerif",17));
		Text text3 = new Text(30, 200, "Team Members: Elyes Bradai, Ziad Agha, Amine Ait Yakoub");
		text3.setFont(new Font("SansSerif",10));
		VBox vbox = new VBox(text1, text2,text3);
		vbox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(vbox, 1200, 600);
		scene.getStylesheets().add(Simulation.class.getResource("Style.css").toExternalForm());
		Stage aboutPageStage = new Stage();
		aboutPageStage.setTitle("About Page");
		aboutPageStage.setScene(scene);
		aboutPageStage.show();
	}

	public void showstartmenu() throws IOException{
		Stage stage = new Stage();
		FXMLLoader fxmlloader = new FXMLLoader(MainClass.class.getResource("oldFX.fxml"));
		Scene scene = new Scene(fxmlloader.load(), 1300, 1000);
		scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		stage.setTitle("Magnet Ai: Simulation designer");
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}
	
	@FXML
	protected void onHelloButtonClick(){
		welcomeText.setText("Welcome to JavaFX Application!");
	}
	
}

