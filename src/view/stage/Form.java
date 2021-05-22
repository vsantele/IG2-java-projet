package view.stage;

import business.DateGenerator;
import controller.BookingController;
import exception.data.*;
import exception.model.booking.InvalidBookingException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import view.component.DateCell;
import view.component.SessionCell;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Form extends Stage{
  private Scene scene;
  private GridPane grid;
  private BookingController controller;
  
  private ArrayList<Charity> charities;
  private ArrayList<Activity> activities;
  private ArrayList<Session> sessions;
  private ArrayList<LocalDate> dates;
  
  private Boolean isUpdate;
  private Booking booking;

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
  
  private Alert infoAlert;
  private Alert errorAlert;

  
  public Form(Stage primaryStage, BookingController controller) {
    this.controller = controller;
    grid = new GridPane();
    grid.setVgap(10.0);
    grid.setHgap(5.0);
    grid.setPadding(new Insets(0, 10, 0, 10));
    scene = new Scene(grid);
  
    isUpdate = false;

    firstnameTextField = new TextField();
    firstnameTextField.setPromptText("John");
    lastnameTextField = new TextField();
    lastnameTextField.setPromptText("Doe");
    phoneTextField = new TextField();
    phoneTextField.setPromptText("0123/45.67.89");
    emailTextField = new TextField();
    emailTextField.setPromptText("john.doe@email.com");
    birthdatePicker = new DatePicker();
    amountTextField = new TextField();
    amountTextField.setPromptText("10");
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
      charities = controller.getCharities();
      charityPicker.setItems(FXCollections.observableArrayList(charities));
    } catch (GetException e) {
      e.printStackTrace();
    }
  
    activityPicker = new ComboBox<>();
  
    try {
      activities = controller.getActivities();
      activityPicker.setItems(FXCollections.observableArrayList(activities));
    } catch (GetException e) {
      e.printStackTrace();
    }
    
    activityPicker.setButtonCell(new ActivityCell());
    activityPicker.setCellFactory(listView -> new ActivityCell());
    activityPicker.valueProperty().addListener(observable -> {
      try {
        Activity selectedActivity = activityPicker.getValue();
        if (selectedActivity != null) {
          
          sessions = controller.getSessions(selectedActivity);
          sessionPicker.setItems(FXCollections.observableArrayList(sessions));
          sessionPicker.setDisable(false);
        } else {
          sessionPicker.getSelectionModel().select(null);
          sessionPicker.setDisable(true);
        }
        if (datePicker.getValue() != null) {
          datePicker.setItems(null);
        }
      } catch (GetException e) {
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
          dates = DateGenerator.getDates(sessionPicker.getValue());
          datePicker.setItems(FXCollections.observableArrayList(dates));
          datePicker.setDisable(false);
        } else {
          datePicker.setDisable(true);
        }
      } catch (GetException e) {
        e.printStackTrace();
      }
    });
    datePicker = new ComboBox<>();
    datePicker.setButtonCell(new DateCell());
    datePicker.setCellFactory(listView -> new DateCell());
    datePicker.setDisable(true);
    confirmBtn = new Button("Confirmer");
    confirmBtn.setOnAction(new ConfirmHandler());
    cancelBtn = new Button("Annuler");
    cancelBtn.setOnAction(event -> {
      hide();
    });
    
    infoAlert = new Alert(Alert.AlertType.INFORMATION);
    errorAlert = new Alert(Alert.AlertType.ERROR);
    
    grid.add(new Label("Prénom * "), 0,0);
    grid.add(firstnameTextField, 1,0);
    grid.add(new Label("Nom * "), 0,1);
    grid.add(lastnameTextField, 1,1);
    grid.add(new Label("Téléphone * "), 0,2);
    grid.add(phoneTextField, 1,2);
    grid.add(new Label("Email "), 0,3);
    grid.add(emailTextField, 1,3);
    grid.add(new Label("Date de Naissance "), 0,4);
    grid.add(birthdatePicker, 1, 4);
    grid.add(new Label("Montant * "), 0,5);
    grid.add(amountTextField,1,5);
    grid.add(new Label("Déjà payé * "), 0,6);
    grid.add(isPaidBox, 1,6);
    grid.add(new Label("Association * "), 0,7);
    grid.add(charityPicker,1,7);
    grid.add(new Label("Activité * "), 0,8);
    grid.add(activityPicker, 1,8);
    grid.add(new Label("Session * "), 0,9);
    grid.add(sessionPicker, 1,9);
    grid.add(new Label("Date * "),0,10);
    grid.add(datePicker, 1,10);
    grid.add(confirmBtn, 0,11);
    grid.add(cancelBtn, 1,11);


    this.initModality(Modality.APPLICATION_MODAL);
    this.initOwner(primaryStage);
    this.setTitle("Formulaire");
    this.setScene(scene);
    this.setWidth(350);
    this.setHeight(450);
  }
  
  public void setBooking(Booking booking) {
    this.booking = booking;
    if (booking == null) {
      firstnameTextField.setText(null);
      lastnameTextField.setText(null);
      phoneTextField.setText(null);
      emailTextField.setText(null);
      birthdatePicker.setValue(null);
      amountTextField.setText(null);
      isPaidYes.setSelected(true);
      charityPicker.getSelectionModel().select(null);
      activityPicker.getSelectionModel().select(null);
    } else {
      firstnameTextField.setText(booking.getFirstname());
      lastnameTextField.setText(booking.getLastname());
      phoneTextField.setText(booking.getPhone());
      emailTextField.setText(booking.getEmail());
      birthdatePicker.setValue(booking.getBirthdate());
      
      amountTextField.setText(NumberFormat.getInstance().format(booking.getAmount()));
      
      if (booking.isPaid()) {
        isPaidYes.setSelected(true);
      } else {
        isPaidNo.setSelected(true);
      }
      
      try {
        Charity charity = booking.getCharity();
        if (charity == null) {
          charity = controller.getCharity(booking.getCharityCode());
        }
        charityPicker.getSelectionModel().select(charity);
        
      } catch (GetException e) {
        e.printStackTrace();
      }
      
      try {
        Activity activity = controller.getActivity(booking.getSessionId());
        activityPicker.getSelectionModel().select(activity);
      } catch (GetException e) {
        e.printStackTrace();
      }
      
      sessionPicker.getSelectionModel().select(new Session(booking.getSessionId()));
      
      datePicker.getSelectionModel().select(booking.getDate());
    }
  }
  
  public void setUpdate(Boolean update) {
    isUpdate = update;
  }
  
  private class ConfirmHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
      String firstname = firstnameTextField.getText();
      String lastname = lastnameTextField.getText();
      String phone = phoneTextField.getText();
      String email = emailTextField.getText();
      LocalDate birthdate = birthdatePicker.getValue();
      Double amount;
      try {
        if (amountTextField.getText() == null) amount = null;
        else {
          amount = (Double) NumberFormat.getInstance().parse(amountTextField.getText());
        }
      } catch (ParseException | ClassCastException e ) {
        errorAlert.setTitle("Montant non valide");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Veuillez entrer un nombre valide. Le séparateur des décimales est la virgule (,)");
        errorAlert.showAndWait();
        return;
      }
      
      RadioButton isPaidSelected = (RadioButton) isPaidGroup.getSelectedToggle();
      Boolean isPaid = isPaidSelected.getText().equals(isPaidYesValue);
      
      Charity charity = charityPicker.getValue();
      
      if (charity == null ) {
        errorAlert.setTitle("Association requise");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Veuillez entrez une association pour réserver une séance");
        errorAlert.showAndWait();
        return;
      }
      
      if(activityPicker.getValue() == null) {
        errorAlert.setTitle("Activité requise");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Veuillez entrez une activité pour réserver une séance");
        errorAlert.showAndWait();
        return;
      }
      
      Session session = sessionPicker.getValue();
      
      if (session == null) {
        errorAlert.setTitle("Session requise");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Veuillez entrez une session pour réserver une séance");
        errorAlert.showAndWait();
        return;
      }
      
      LocalDate date = datePicker.getValue();
  
      try {
        if (isUpdate) {
          if (booking == null) {
            errorAlert.setTitle("Aucune réservation");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Vous tentez de faire une mise à jour d'une réservation inexistante");
            errorAlert.showAndWait();
            return;
          } else {
            booking.setFirstname(firstname);
            booking.setLastname(lastname);
            booking.setPhone(phone);
            booking.setEmail(email);
            booking.setBirthdate(birthdate);
            booking.setAmount(amount);
            booking.setPaid(isPaid);
            booking.setCharity(charity);
            booking.setCharityCode(null);
            booking.setSession(session);
            booking.setSessionId(null);
            booking.setDate(date);
            Boolean isSuccess = controller.updateBooking(booking);
            if (isSuccess) {
              close();
              infoAlert.setTitle("Succès");
              infoAlert.setHeaderText(null);
              infoAlert.setContentText("Réservation modifiée avec succès");
              infoAlert.show();
            } else {
              errorAlert.setTitle("Erreur");
              errorAlert.setHeaderText(null);
              errorAlert.setContentText("La séance est pleine. Veuillez en sélectionner une autre");
              errorAlert.show();
            }
          }
        } else {
          Booking booking = new Booking(firstname, lastname, amount, isPaid, phone, birthdate, email, date, charity, session);
          Boolean isSuccess = controller.addBooking(booking);
          if (isSuccess) {
            close();
            infoAlert.setTitle("Succès");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("Réservation ajoutée avec succès");
            infoAlert.show();
          } else {
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("La séance est pleine. Veuillez en sélectionner une autre");
            errorAlert.show();
          }
        }
      } catch (InvalidBookingException e) {
        errorAlert.setTitle("Erreur");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(e.getMessage());
        errorAlert.show();
      }
      catch (UpdateBookingException | AddBookingException e) {
        e.printStackTrace();
      }
  
    }
  }
  
}
