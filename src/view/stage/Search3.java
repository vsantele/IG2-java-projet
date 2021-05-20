package view.stage;

import controller.BookingController;
import exception.data.GetActivitiesException;
import exception.data.GetCharityAtHourException;
import exception.data.GetCharityException;
import exception.data.GetPeoplePerActivityAndCharityException;
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
import model.AmountActivity;
import model.Booking;
import model.Charity;
import view.component.ActivityCell;
import view.component.CharityCell;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Search3 extends Stage {
  private Scene scene;
  private BorderPane pane;
  private HBox top;
  
  private DatePicker startDatePicker;
  private DatePicker endDatePicker;
  private ComboBox<Integer> hourPicker;
  private ComboBox<Integer> minutePicker;

  private Button submitBtn;
  private TableView<Charity> center;
  private BookingController controller;
  
  private Integer selectedHour;
  private Integer selectedMinute;
  private LocalDate selectedStartDate;
  private LocalDate selectedEndDate;
  
  public Search3(Stage primaryStage, BookingController controller) {
    this.controller = controller;
    pane = new BorderPane();
    scene = new Scene(pane);
  
    hourPicker = new ComboBox<>();
    ArrayList<Integer> hours = new ArrayList<>();
    for(int hour = 0; hour < 24; hour++) {
      hours.add(hour);
    }
    hourPicker.setItems(FXCollections.observableArrayList(hours));
    hourPicker.valueProperty().addListener(observable -> {
      selectedHour = hourPicker.getValue();
    });
    hourPicker.getSelectionModel().select(16);
  
    minutePicker = new ComboBox<>();
    ArrayList<Integer> minutes = new ArrayList<>();
    for(int minute = 0; minute < 60; minute += 15) {
      minutes.add(minute);
    }
    minutePicker.setItems(FXCollections.observableArrayList(minutes));
    minutePicker.valueProperty().addListener(observable -> {
      selectedMinute = minutePicker.getValue();
    });
    minutePicker.getSelectionModel().select(0);
    
    submitBtn = new Button("Rechercher");
    submitBtn.setOnAction(event -> {
      if (selectedHour != null && selectedMinute != null) {
        LocalTime time = LocalTime.of(selectedHour, selectedMinute);
        try {
          ArrayList<Charity> results = controller.getCharityAtHour(time);
          center.setItems(FXCollections.observableArrayList(results));
        } catch (GetCharityAtHourException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("Champs manquant");
      }
    });
    
    top = new HBox(10, new Label("Heure: "), hourPicker, new Label(":"), minutePicker, submitBtn);
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
    this.setTitle("Recherche");
    this.setScene(scene);
    this.setHeight(600);
    this.setWidth(350);
  }
}
