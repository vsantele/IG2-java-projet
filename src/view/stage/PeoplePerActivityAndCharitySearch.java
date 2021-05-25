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
import model.Activity;
import model.Booking;
import model.Charity;
import view.component.ActivityCell;
import view.component.CharityCell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PeoplePerActivityAndCharitySearch extends Stage {
  private final ComboBox<Activity> activityPicker;
  private final ComboBox<Charity> charityPicker;
  private final TableView<Booking> center;
  
  private final Alert errorAlert;
  
  private Activity selectedActivity;
  private Charity selectedCharity;
  
  public PeoplePerActivityAndCharitySearch(Stage primaryStage, BookingController controller) {
    BorderPane pane = new BorderPane();
    Scene scene = new Scene(pane);
    errorAlert = new Alert(Alert.AlertType.ERROR);
  
    activityPicker = new ComboBox<>();
    activityPicker.setButtonCell(new ActivityCell());
    activityPicker.setCellFactory(listView -> new ActivityCell());
    try {
      ArrayList<Activity> activities = controller.getActivities();
      activityPicker.setItems(FXCollections.observableArrayList(activities));
    } catch (GetException e) {
      errorAlert.setHeaderText(e.getMessage());
      errorAlert.setContentText(e.getDetails());
      errorAlert.showAndWait();
    }
    activityPicker.valueProperty().addListener(observable -> selectedActivity = activityPicker.getValue());
    
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
  
    charityPicker.valueProperty().addListener(observable -> selectedCharity = charityPicker.getValue());
  
    center = new TableView<>();
  
    Button submitBtn = new Button("Rechercher");
    submitBtn.setOnAction(event -> {
      if (selectedActivity != null && selectedCharity != null) {
        try {
          ArrayList<Booking> results = controller.getPeoplePerActivityAndCharity(selectedActivity, selectedCharity);
          center.setItems(FXCollections.observableArrayList(results));
        } catch (GetException e) {
          errorAlert.setHeaderText(e.getMessage());
          errorAlert.setContentText(e.getDetails());
          errorAlert.showAndWait();
        }
      }
    });
  
    HBox top = new HBox(10, new Label("Activité: "), activityPicker, new Label("Association: "), charityPicker, submitBtn);
    top.setPadding(new Insets(15, 12, 15, 12));
    pane.setTop(top);
    
    TableColumn<Booking, String> firstnameCol = new TableColumn<>("Prénom");
    firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    
    TableColumn<Booking, String> lastnameCol = new TableColumn<>("Nom");
    lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
  
    TableColumn<Booking, String> birthdateCol = new TableColumn<>("Date de Naissance");
    birthdateCol.setCellValueFactory(cellData -> {
      LocalDate date = cellData.getValue().getBirthdate();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
      return new SimpleStringProperty(date.format(formatter));
    });
  
    TableColumn<Booking, String> amountCol = new TableColumn<>("Montant");
    amountCol.setCellValueFactory(cellData -> {
      Double amount = cellData.getValue().getAmount();
      return new SimpleStringProperty(String.format("%.2f", amount) + " €");
    });
    
    center.getColumns().add(firstnameCol);
    center.getColumns().add(lastnameCol);
    center.getColumns().add(birthdateCol);
    center.getColumns().add(amountCol);
    
    pane.setCenter(center);
    
    
    this.initModality(Modality.APPLICATION_MODAL);
    this.initOwner(primaryStage);
    this.setTitle("Réservation pour Activité et Association");
    this.setScene(scene);
    this.setWidth(650);
    this.setHeight(300);
    
  }
  
  public void reset() {
    activityPicker.getSelectionModel().select(null);
    charityPicker.getSelectionModel().select(null);
    center.setItems(null);
  }
}
