package view.stage;

import controller.BookingController;
import exception.data.GetException;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class Total extends Stage {
  private final Text amountText;
  private final BookingController controller;
  
  private final Alert errorAlert;
  
  public Total(Stage primaryStage, BookingController controller) {
    amountText = new Text("chargement...");
    HBox pane = new HBox(new Text("Montant total de l'évènement: "), amountText);
    Scene scene = new Scene(pane);
    errorAlert = new Alert(Alert.AlertType.ERROR);
    
    this.controller = controller;
    this.initOwner(primaryStage);
    this.setTitle("Montant total");
    this.setScene(scene);
    this.setHeight(100);
    this.setWidth(300);
    this.setX(1600);
    this.setY(114);
    this.setResizable(false);
  }
  
  public void update() throws GetException {
    try {
      Double total = controller.getTotal();
      Platform.runLater(() -> amountText.setText(String.format("%.2f", total) + "€"));
    } catch (GetException e) {
      Platform.runLater(() -> {
        amountText.setText("Erreur");
        errorAlert.setHeaderText(e.getMessage());
        errorAlert.setContentText(e.getDetails());
        errorAlert.showAndWait();
      });
      throw e;
    }
  }
}
