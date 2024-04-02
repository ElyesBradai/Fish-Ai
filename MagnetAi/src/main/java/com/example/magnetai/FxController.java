package com.example.magnetai;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class FxController{
	
	
	public Button saveButton;
	public Button startButton;
	public Button resetButton;
	public Button pauseButton;
	@FXML
	private Label welcomeText;
	@FXML
	private Pane SimDisplayPane;
	@FXML
	private Circle ChargeSelector;
	@FXML
	private Rectangle ObstacleSelector;
	@FXML
	private Rectangle FLSelector;
	@FXML
	private Rectangle SCSelector;

	@FXML
	public void initialize(){
		
		startbutton();
		
	}
	
	public void startbutton(){
		HBox root = new HBox();
		root.setSpacing(Simulation.GRID_SIZE_X * Simulation.SQUARE_SIZE);
		SimDisplayPane.getChildren().add(root);
		VBox vb1 = new VBox();
		root.getChildren().addAll(vb1);
		SimulationDisplay s1 = new SimulationDisplay();
		vb1.getChildren().add(s1.getSimPane());
		Charge c1 = new Charge(1, ChargeType.POSITIVE);
		s1.addToMap(c1, 6);
		Simulation s2 = new Simulation();
		Simulation s3 = new Simulation();
		Simulation s4 = new Simulation();
		Simulation s5 = new Simulation();
		Simulation s6 = new Simulation();
		Simulation s7 = new Simulation();
		Simulation s8 = new Simulation();
		// Create an object property to hold the selected rectangle
		ObjectProperty<Shape> selectedShape = new SimpleObjectProperty<>();
		Circle selected = new Circle(50);
		selected.setStrokeWidth(3);
		// Bind the fill property of the circle to the fill property of the selected rectangle
		selected.fillProperty().bind(Bindings.createObjectBinding(() ->
				selectedShape.get() != null ? selectedShape.get().getFill() : Color.BLACK, selectedShape));
		
		
		ChargeSelector.setOnMouseClicked(event -> {
			s1.setSelectedComponentType(Charge.TYPE);
			System.out.println(Charge.TYPE);
			selectedShape.set(ChargeSelector);
		});
		ObstacleSelector.setOnMouseClicked(event -> {
			s1.setSelectedComponentType(Obstacle.TYPE);
			System.out.println(Obstacle.TYPE);
			selectedShape.set(ObstacleSelector);
		});
		FLSelector.setOnMouseClicked(event -> {
			s1.setSelectedComponentType(FinishLine.TYPE);
			System.out.println(FinishLine.TYPE);
			selectedShape.set(FLSelector);
		});
		SCSelector.setOnMouseClicked(event -> {
			s1.setSelectedComponentType(Superconductor.TYPE);
			System.out.println(Superconductor.TYPE);
			selectedShape.set(SCSelector);
		});
		
		Button b1 = new Button("Save");
		b1.setOnAction(event -> {
			
			s1.saveDesign();
			s1.showAllSimulations();
		});
//        VBox selectorBox = new VBox(ObstacleSelector,FLSelector,SCSelector,selected,b1);
//        root.getChildren().add(selectorBox);
//        Scene scene = new Scene(root, 1200, 800);
//        Stage stage = new Stage();
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
	}
	
	@FXML
	protected void onHelloButtonClick(){
		welcomeText.setText("Welcome to JavaFX Application!");
	}
	
}

