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
  private Scene scene;
  private BorderPane pane;
  private VBox top;
  
  private DatePicker startDatePicker;
  private DatePicker endDatePicker;
  private ComboBox<Integer> hourPicker;
  private ComboBox<Integer> minutePicker;

  private Button submitBtn;
  private TableView<Charity> center;
  private BookingController controller;
  
  private Alert errorAlert;
  
  public CharityAtHourSearch(Stage primaryStage, BookingController controller) {
    this.controller = controller;
    pane = new BorderPane();
    scene = new Scene(pane);
    
    errorAlert = new Alert(Alert.AlertType.ERROR);
    errorAlert.setTitle("Erreur");
  
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
    
    submitBtn = new Button("Rechercher");
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
        errorAlert.setHeaderText("Erreur lors de la recherche");
        errorAlert.setContentText(e.getMessage());
        errorAlert.show();
      }
      
    });
    
    top = new VBox(10, new HBox(10, new Label("Heure: "), hourPicker, new Label(":"), minutePicker), new HBox(new Label("Date de début: "), startDatePicker),new HBox( new Label("Date de fin: "), endDatePicker),new HBox( submitBtn));
    pane.setTop(top);
    
    center = new TableView<>();
    TableColumn<Charity, String> charityCol = new TableColumn<>("Association");
    charityCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    TableColumn<Charity, String> contactCol = new TableColumn<>("Contact");
    contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
    
    center.getColumns().add(charityCol);
    center.getColumns().add(contactCol);
    
    pane.setPadding(new Insets(15, 12, 15, 12));
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
    center.setItems(null);

  }
}
