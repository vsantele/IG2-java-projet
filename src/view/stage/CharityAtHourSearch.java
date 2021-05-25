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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Charity;

import java.time.LocalTime;
import java.util.ArrayList;

public class CharityAtHourSearch extends Stage {
  
  private final DatePicker startDatePicker;
  private final DatePicker endDatePicker;
  private final ComboBox<Integer> hourPicker;
  private final ComboBox<Integer> minutePicker;
  
  private final TableView<Charity> center;
  
  private final Alert errorAlert;
  
  public CharityAtHourSearch(Stage primaryStage, BookingController controller) {
    BorderPane pane = new BorderPane();
    Scene scene = new Scene(pane);
    center = new TableView<>();
    
    errorAlert = new Alert(Alert.AlertType.ERROR);
  
    hourPicker = new ComboBox<>();
    ArrayList<Integer> hours = new ArrayList<>();
    for(int hour = 0; hour < 24; hour++) {
      hours.add(hour);
    }
    hourPicker.setItems(FXCollections.observableArrayList(hours));
    hourPicker.getSelectionModel().select(16);
  
    minutePicker = new ComboBox<>();
    ArrayList<Integer> minutes = new ArrayList<>();
    for(int minute = 0; minute < 60; minute += 15) {
      minutes.add(minute);
    }
    minutePicker.setItems(FXCollections.observableArrayList(minutes));
    minutePicker.getSelectionModel().select(0);
    
    startDatePicker = new DatePicker();
    
    endDatePicker = new DatePicker();
  
    Button submitBtn = new Button("Rechercher");
    submitBtn.setOnAction(event -> {
      if (hourPicker.getValue() == null) {
        errorAlert.setHeaderText("Heure non valide");
        errorAlert.show();
        return;
      }
      if (minutePicker.getValue() == null) {
        errorAlert.setHeaderText("Minute non valide");
        errorAlert.show();
        return;
      }
      if (startDatePicker.getValue() == null) {
        errorAlert.setHeaderText("Date de début non valide");
        errorAlert.show();
        return;
      }
      if (endDatePicker.getValue() == null) {
        errorAlert.setHeaderText("Date de fin non valide");
        errorAlert.show();
        return;
      }
      
      if (endDatePicker.getValue().isBefore(startDatePicker.getValue())) {
        errorAlert.setHeaderText("La date de fin doit être supérieur à la date de début");
        errorAlert.show();
        return;
      }
      
      LocalTime time = LocalTime.of(hourPicker.getValue(), minutePicker.getValue());
      try {
        ArrayList<Charity> results = controller.getCharityAtHour(time);
        center.setItems(FXCollections.observableArrayList(results));
      } catch (GetException e) {
        errorAlert.setHeaderText(e.getMessage());
        errorAlert.setContentText(e.getDetails());
        errorAlert.showAndWait();
      }
      
    });
  
    VBox top = new VBox(10, new HBox(10, new Label("Heure: "), hourPicker, new Label(":"), minutePicker), new HBox(new Label("Date de début: "), startDatePicker), new HBox(new Label("Date de fin: "), endDatePicker), new HBox(submitBtn));
    top.setPadding(new Insets(15, 12, 15, 12));
    pane.setTop(top);
    
    
    TableColumn<Charity, String> charityCol = new TableColumn<>("Association");
    charityCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    TableColumn<Charity, String> contactCol = new TableColumn<>("Contact");
    contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
    TableColumn<Charity, String> addressCol = new TableColumn<>("Adresse");
    addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    TableColumn<Charity, String> zipCodeCol = new TableColumn<>("Code Postal");
    zipCodeCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
    TableColumn<Charity, String> countryCol = new TableColumn<>("Pays");
    countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
    
    center.getColumns().setAll(charityCol, contactCol, addressCol, zipCodeCol, countryCol);
    
    pane.setCenter(center);
    
    this.initModality(Modality.APPLICATION_MODAL);
    this.initOwner(primaryStage);
    this.setTitle("Association pour une heure et date");
    this.setScene(scene);
    this.setHeight(600);
    this.setWidth(850);
  }

  public void reset() {
    hourPicker.getSelectionModel().select(null);
    minutePicker.getSelectionModel().select(null);
    startDatePicker.setValue(null);
    endDatePicker.setValue(null);
    center.setItems(null);
  }
}
