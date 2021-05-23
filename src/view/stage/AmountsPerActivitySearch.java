package view.stage;

import controller.BookingController;
import exception.data.GetException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AmountActivity;
import model.Charity;
import view.component.CharityCell;

import java.util.ArrayList;

public class AmountsPerActivitySearch extends Stage {
  private Scene scene;
  private BorderPane pane;
  private HBox top;
  
  private ComboBox<Charity> charityPicker;
  private TableView<AmountActivity> center;
  
  private Alert errorAlert;
  
  private BookingController controller;
  
  public AmountsPerActivitySearch(Stage primaryStage, BookingController controller) {
    this.controller = controller;
    pane = new BorderPane();
    scene = new Scene(pane);
    
    errorAlert = new Alert(Alert.AlertType.ERROR);
    errorAlert.setTitle("Erreur");
  
    charityPicker = new ComboBox<>();
    charityPicker.setButtonCell(new CharityCell());
    charityPicker.setCellFactory(listView -> new CharityCell());
    try {
      ArrayList<Charity> charities = controller.getCharities();
      charityPicker.setItems(FXCollections.observableArrayList(charities));
    } catch (GetException e) {
      errorAlert.setHeaderText(e.getMessage());
      errorAlert.setContentText(e.getDetails());
      errorAlert.showAndWait();
    }
  
    charityPicker.valueProperty().addListener(observable -> {
      Charity selectedCharity = charityPicker.getValue();
      if (selectedCharity != null) {
        try {
          ArrayList<AmountActivity> results = controller.getAmountsPerActivity(selectedCharity);
          center.setItems(FXCollections.observableArrayList(results));
        } catch (GetException e) {
          errorAlert.setHeaderText(e.getMessage());
          errorAlert.setContentText(e.getDetails());
          errorAlert.showAndWait();
        }
      }
      
    });
    
    top = new HBox(10, new Label("Associations: "), charityPicker);
    pane.setPadding(new Insets(15, 12, 15, 12));
    pane.setTop(top);
    
    center = new TableView<>();
    TableColumn<AmountActivity, String> activityCol = new TableColumn<>("Activité");
    activityCol.setCellValueFactory(new PropertyValueFactory<>("activity"));
    TableColumn<AmountActivity, String> amountCol = new TableColumn<>("Montant");
    amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
    
    center.getColumns().add(activityCol);
    center.getColumns().add(amountCol);
    
    pane.setCenter(center);
    pane.setPadding(new Insets(15, 12, 15, 12));
    
    this.initModality(Modality.APPLICATION_MODAL);
    this.initOwner(primaryStage);
    this.setTitle("Recettes par activité");
    this.setScene(scene);
    this.setWidth(300);
    this.setHeight(500);
  }

  public void update() {
    charityPicker.getSelectionModel().select(null);
    center.setItems(null);
  }
}
