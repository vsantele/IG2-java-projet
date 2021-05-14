package view.stage;

import business.BookingManager;
import business.DateGenerator;
import exception.data.GetActivitiesException;
import exception.data.GetCharityException;
import exception.data.GetDatesException;
import exception.data.GetSessionsException;
import exception.model.booking.InvalidBookingException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import model.Activity;
import model.Booking;
import model.Charity;
import model.Session;
import view.component.ActivityCell;
import view.component.CharityCell;
import view.component.SessionCell;

import java.time.LocalDate;
import java.util.ArrayList;

public class Form extends Stage{
  private Stage stage;
  private Scene scene;
  private GridPane grid;

  private TextField firstnameTextField;
  private TextField lastnameTextField;
  private TextField phoneTextField;
  private TextField emailTextField;
  private DatePicker birthdatePicker;
  private TextField amountTextField;
  private RadioButton isPaidYes;
  private String isPaidYesValue = "Oui";
  private RadioButton isPaidNo;
  private String isPaidNoValue = "Non";
  private HBox isPaidBox;
  private ToggleGroup isPaidGroup;
  private ComboBox<Charity> charityPicker;
  private ComboBox<Activity> activityPicker;
  private ComboBox<Session> sessionPicker;
  private ComboBox<LocalDate> datePicker;
  private Button confirmBtn;
  private Button cancelBtn;

  
  public Form(Stage primaryStage, BookingManager bookingManager) {
    grid = new GridPane();
    grid.setVgap(10.0);
    grid.setHgap(5.0);
    scene = new Scene(grid);

    firstnameTextField = new TextField();
    lastnameTextField = new TextField();
    phoneTextField = new TextField();
    emailTextField = new TextField();
    birthdatePicker = new DatePicker();
    amountTextField = new TextField();
    amountTextField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
    isPaidYes = new RadioButton("Oui");
    isPaidNo = new RadioButton("Non");
    isPaidGroup = new ToggleGroup();
    isPaidYes.setToggleGroup(isPaidGroup);
    isPaidYes.setSelected(true);
    isPaidNo.setToggleGroup(isPaidGroup);
    isPaidBox = new HBox(10, isPaidYes, isPaidNo);
    charityPicker = new ComboBox<>();
    charityPicker.setButtonCell(new CharityCell());
    charityPicker.setCellFactory(listView -> new CharityCell());
    try {
      ArrayList<Charity> charities = bookingManager.getCharities();
      charityPicker.setItems(FXCollections.observableArrayList(charities));
    } catch (GetCharityException e) {
      e.printStackTrace();
    }
  
    activityPicker = new ComboBox<>();
  
    try {
      ArrayList<Activity> activities = bookingManager.getActivities();
      activityPicker.setItems(FXCollections.observableArrayList(activities));
    } catch (GetActivitiesException e) {
      e.printStackTrace();
    }
    
    activityPicker.setButtonCell(new ActivityCell());
    activityPicker.setCellFactory(listView -> new ActivityCell());
    activityPicker.valueProperty().addListener(observable -> {
      try {
        Activity selectedActivity = activityPicker.getValue();
        if (selectedActivity != null) {
          
          ArrayList<Session> sessions = bookingManager.getSessions(selectedActivity);
          sessionPicker.setItems(FXCollections.observableArrayList(sessions));
          sessionPicker.setDisable(false);
        } else {
          sessionPicker.setDisable(true);
        }
        if (datePicker.getValue() != null) {
          datePicker.setItems(null);
        }
      } catch (GetSessionsException e) {
        e.printStackTrace();
      }
    });
    
    sessionPicker = new ComboBox<>();
    sessionPicker.setButtonCell(new SessionCell());
    sessionPicker.setCellFactory(listView -> new SessionCell());
    sessionPicker.setDisable(true);
    sessionPicker.valueProperty().addListener(observable -> {
      try {
        Session selectedSession = sessionPicker.getValue();
        if (selectedSession != null) {
          ArrayList<LocalDate> dates = DateGenerator.getDates(sessionPicker.getValue());
          datePicker.setItems(FXCollections.observableArrayList(dates));
          datePicker.setDisable(false);
        } else {
          datePicker.setDisable(true);
        }
      } catch (GetDatesException e) {
        e.printStackTrace();
      }
    });
    datePicker = new ComboBox<>();
    datePicker.setButtonCell(new view.component.DateCell());
    datePicker.setCellFactory(listView -> new view.component.DateCell());
    datePicker.setDisable(true);
    confirmBtn = new Button("Confirmer");
    confirmBtn.setOnAction(new ConfirmHandler());
    cancelBtn = new Button("Annuler");
    
    grid.add(new Label("Prénom: "), 0,0);
    grid.add(firstnameTextField, 1,0);
    grid.add(new Label("Nom: "), 0,1);
    grid.add(lastnameTextField, 1,1);
    grid.add(new Label("Téléphone: "), 0,2);
    grid.add(phoneTextField, 1,2);
    grid.add(new Label("Email: : "), 0,3);
    grid.add(emailTextField, 1,3);
    grid.add(new Label("Date de Naissance: "), 0,4);
    grid.add(birthdatePicker, 1, 4);
    grid.add(new Label("Montant: "), 0,5);
    grid.add(amountTextField,1,5);
    grid.add(new Label("Déjà payé: "), 0,6);
    grid.add(isPaidBox, 1,6);
    grid.add(new Label("Association: "), 0,7);
    grid.add(charityPicker,1,7);
    grid.add(new Label("Activité: "), 0,8);
    grid.add(activityPicker, 1,8);
    grid.add(new Label("Session: "), 0,9);
    grid.add(sessionPicker, 1,9);
    grid.add(new Label("Date: "),0,10);
    grid.add(datePicker, 1,10);
    grid.add(confirmBtn, 0,11);
    grid.add(cancelBtn, 1,11);


    this.initModality(Modality.APPLICATION_MODAL);
    this.initOwner(primaryStage);
    this.setTitle("Formulaire");
    this.setScene(scene);
    this.show();
  }
  
  public void setBooking(Booking booking) {
    firstnameTextField.setText(booking.getFirstname());
    lastnameTextField.setText(booking.getLastname());
    phoneTextField.setText(booking.getPhone());
    emailTextField.setText(booking.getEmail());
    birthdatePicker.setValue(booking.getBirthdate());
    amountTextField.setText(booking.getAmount().toString());
    
    if (booking.isPaid()) {
      isPaidYes.setSelected(true);
    } else {
      isPaidNo.setSelected(true);
    }
    
    charityPicker.setValue(booking.getCharity());
  }
  
  private class ConfirmHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
      String firstname = firstnameTextField.getText();
      String lastname = lastnameTextField.getText();
      String phone = phoneTextField.getText();
      String email = emailTextField.getText();
      LocalDate birthdate = birthdatePicker.getValue();
      Double amount = null;
      try {
        amount = Double.parseDouble(amountTextField.getText());
      } catch ( NumberFormatException e ) {
        System.out.println("Mauvais nombre");
      }
      
      RadioButton isPaidSelected = (RadioButton) isPaidGroup.getSelectedToggle();
      Boolean isPaid = isPaidSelected.getText().equals(isPaidYesValue);
      
      Charity charity = charityPicker.getValue();
      Session session = sessionPicker.getValue();
      LocalDate date = datePicker.getValue();
  
      try {
        Booking booking = new Booking(lastname, firstname, amount, isPaid, phone, birthdate, email, date, charity, session);
        System.out.println("booking = " + booking);
      } catch (InvalidBookingException e) {
        e.printStackTrace();
      }
  
    }
  }
  
}
