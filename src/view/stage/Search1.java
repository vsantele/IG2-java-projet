package view.stage;

import business.DateGenerator;
import controller.BookingController;
import exception.data.GetAmountsPerActivityException;
import exception.data.GetCharityException;
import exception.data.GetDatesException;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AmountActivity;
import model.Charity;
import model.Session;
import view.component.CharityCell;

import java.util.ArrayList;

public class Search1 extends Stage {
  private Scene scene;
  private BorderPane pane;
  private HBox top;
  private ComboBox<Charity> charityPicker;
  private TableView<AmountActivity> center;
  private BookingController controller;
  
  public Search1(Stage primaryStage, BookingController controller) {
    this.controller = controller;
    pane = new BorderPane();
    scene = new Scene(pane);
  
    charityPicker = new ComboBox<>();
    charityPicker.setButtonCell(new CharityCell());
    charityPicker.setCellFactory(listView -> new CharityCell());
    try {
      ArrayList<Charity> charities = controller.getCharities();
      charityPicker.setItems(FXCollections.observableArrayList(charities));
    } catch (GetCharityException e) {
      e.printStackTrace();
    }
  
    charityPicker.valueProperty().addListener(observable -> {
      Charity selectedCharity = charityPicker.getValue();
      if (selectedCharity != null) {
        try {
          ArrayList<AmountActivity> results = controller.getAmountsPerActivity(selectedCharity);
          center.setItems(FXCollections.observableArrayList(results));
        } catch (GetAmountsPerActivityException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("association manquantes");
      }
    });
    
    top = new HBox(10, new Label("Associations: "), charityPicker);
    pane.setTop(top);
    
    center = new TableView<>();
    TableColumn<AmountActivity, String> activityCol = new TableColumn<>("Activité");
    activityCol.setCellValueFactory(new PropertyValueFactory<>("activity"));
    TableColumn<AmountActivity, String> amountCol = new TableColumn<>("Montant");
    amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
    
    center.getColumns().add(activityCol);
    center.getColumns().add(amountCol);
    
    pane.setCenter(center);
    
    this.initModality(Modality.APPLICATION_MODAL);
    this.initOwner(primaryStage);
    this.setTitle("Recherche");
    this.setScene(scene);
  }
}
