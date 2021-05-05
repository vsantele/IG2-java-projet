package view.stage;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Form {
  private Stage stage;
  private Scene scene;
  private GridPane grid;
  
  public Form(Stage primaryStage) {
    stage = new Stage();
    grid = new GridPane();
    scene = new Scene(grid);
    
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setTitle("Formulaire");
    stage.setScene(scene);
    stage.show();
  }
}
