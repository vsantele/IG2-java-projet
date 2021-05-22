package view.stage;

import controller.BookingController;
import exception.data.GetException;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Total extends Stage {
  private Scene scene;
  public HBox pane;
  public Label amountLabel;
  public BookingController controller;
  
  public Total(Stage primaryStage, BookingController controller) {
    amountLabel = new Label("chargement...");
    pane = new HBox(new Label("Montant total de l'évènement: "), amountLabel);
    scene = new Scene(pane);
    
    this.controller = controller;
    this.initOwner(primaryStage);
    this.setTitle("Association pour une heure et date");
    this.setScene(scene);
    this.setHeight(100);
    this.setWidth(250);
  }
  
  public void update() {
    try {
      Double total = controller.getTotal();
      Platform.runLater(() -> {
        amountLabel.setText(total + "€");
      });
      
    } catch (GetException e) {
      e.printStackTrace();
    }
  }
}
