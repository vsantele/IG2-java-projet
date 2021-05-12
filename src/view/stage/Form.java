package view.stage;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class Form {
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
  private RadioButton isPaidNo;
  private ToggleGroup isPaidGroup;
  private ChoiceBox<String> charityPicker;
  private ChoiceBox<String> activityPicker;
  private ChoiceBox<String> sessionPicker;
  private ChoiceBox<String> datePicker;
  private Button confirmBtn;
  private Button cancelBtn;

  
  public Form(Stage primaryStage) {
    stage = new Stage();
    grid = new GridPane();
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
    isPaidNo.setToggleGroup(isPaidGroup);
    charityPicker = new ChoiceBox<>();
    activityPicker = new ChoiceBox<>();
    sessionPicker = new ChoiceBox<>();
    datePicker = new ChoiceBox<>();
    confirmBtn = new Button("Confirmer");
    cancelBtn = new Button("Annuler");

    datePicker.getItems().add("Date 1");
    datePicker.getItems().add("Date 2");
    datePicker.getItems().add("Date 3");

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
    grid.add(new HBox(isPaidYes, isPaidNo), 1,6);
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


    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(primaryStage);
    stage.setTitle("Formulaire");
    stage.setScene(scene);
    stage.show();
  }
}
