package view.stage;

import controller.BookingController;
import exception.data.GetException;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Total extends Stage {
  private Scene scene;
  private HBox pane;
  private Label amountLabel;
  private BookingController controller;
  
  private Alert errorAlert;
  
  public Total(Stage primaryStage, BookingController controller) {
    amountLabel = new Label("chargement...");
    pane = new HBox(new Label("Montant total de l'évènement: "), amountLabel);
    scene = new Scene(pane);
    errorAlert = new Alert(Alert.AlertType.ERROR);
    
    this.controller = controller;
    this.initOwner(primaryStage);
    this.setTitle("Association pour une heure et date");
    this.setScene(scene);
    this.setHeight(100);
    this.setWidth(250);
  }
  
  public void update() throws GetException {
    try {
      throw new GetException();
//      Double total = controller.getTotal();
//      Platform.runLater(() -> {
//        amountLabel.setText(total + "€");
//      });
    } catch (GetException e) {
      Platform.runLater(() -> {
        amountLabel.setText("Erreur");
        errorAlert.setHeaderText(e.getMessage());
        errorAlert.setContentText(e.getDetails());
        errorAlert.showAndWait();
      });
      throw e;
    }
  }
}
