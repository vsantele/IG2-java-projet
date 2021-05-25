package view.stage;

import controller.BookingController;
import exception.data.GetException;
import javafx.beans.property.SimpleStringProperty;
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
  
  private final ComboBox<Charity> charityPicker;
  private final TableView<AmountActivity> center;
  
  private final Alert errorAlert;
  
  public AmountsPerActivitySearch(Stage primaryStage, BookingController controller) {
    BorderPane pane = new BorderPane();
    Scene scene = new Scene(pane);
    center = new TableView<>();
    
    errorAlert = new Alert(Alert.AlertType.ERROR);
  
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
  
    HBox top = new HBox(10, new Label("Associations: "), charityPicker);
    top.setPadding(new Insets(15, 12, 15, 12));
    pane.setTop(top);
    
    
    TableColumn<AmountActivity, String> activityCol = new TableColumn<>("Activité");
    activityCol.setCellValueFactory(new PropertyValueFactory<>("activity"));
    TableColumn<AmountActivity, String> amountCol = new TableColumn<>("Montant");
    amountCol.setCellValueFactory(cellData -> {
      Double amount = cellData.getValue().getAmount();
      return new SimpleStringProperty(String.format("%.2f", amount) + " €");
    });
    
    center.getColumns().add(activityCol);
    center.getColumns().add(amountCol);
    
    pane.setCenter(center);
    
    this.initModality(Modality.APPLICATION_MODAL);
    this.initOwner(primaryStage);
    this.setTitle("Recettes par activité");
    this.setScene(scene);
    this.setWidth(300);
    this.setHeight(500);
  }

  public void reset() {
    charityPicker.getSelectionModel().select(null);
    center.setItems(null);
  }
}
