import business.BookingManager;
import controller.BookingController;
import exception.data.GetBookingsException;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Booking;
import view.stage.Form;
import view.stage.Search1;
import view.stage.Search2;
import view.stage.Search3;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main extends Application {
    private BookingManager bookingManager;
    private BookingController bookingController;
    private BorderPane pane;
    private Scene scene;
    
    private Form form;
    private Search1 search1;
    private Search2 search2;
    private Search3 search3;
    
    private Button buttonAdd;
    private Button buttonResearch1;
    private Button buttonResearch2;
    private Button buttonResearch3;
    private HBox hBoxTop;
    private TableView<Booking> center;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage){
        pane = new BorderPane();
        scene = new Scene(pane);
        
        bookingManager = new BookingManager();
        bookingController = new BookingController(bookingManager);
        form = new Form(primaryStage, bookingController);
        search1 = new Search1(primaryStage, bookingController);
        search2 = new Search2(primaryStage, bookingController);
        search3 = new Search3(primaryStage, bookingController);
        
        primaryStage.setTitle("Application");
        primaryStage.centerOnScreen();
        primaryStage.setWidth(1200);
        primaryStage.setHeight(500);
        
        buttonAdd = new Button("Ajouter Réservation");
        buttonAdd.setOnAction(event -> {
            form.setBooking(null);
            form.showAndWait();
            getBookings();
            
        });
        buttonResearch1 = new Button("Recherche 1");
        buttonResearch1.setOnAction(event -> {
            search1.show();
        });
        buttonResearch2 = new Button("Recherche 2");
        buttonResearch2.setOnAction(event -> {
            search2.show();
        });
        buttonResearch3 = new Button("Recherche 3");
        buttonResearch3.setOnAction(event -> {
            search3.show();
        });
        hBoxTop = new HBox(30, buttonAdd, buttonResearch1, buttonResearch2, buttonResearch3);
        hBoxTop.setPadding(new Insets(15, 12, 15, 12));
        hBoxTop.setStyle("-fx-background-color: #336699;");
        pane.setTop(hBoxTop);
        
        center = new TableView<>();
        TableColumn<Booking, String> firstnameCol = new TableColumn<>("Prénom");
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        TableColumn<Booking, String> lastnameCol = new TableColumn<>("Nom");
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        
        TableColumn<Booking, String> phoneCol = new TableColumn<>("Téléphone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TableColumn<Booking, String> birthdateCol = new TableColumn<>("Date de Naissance");
        birthdateCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        TableColumn<Booking, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Booking, String> amountCol = new TableColumn<>("Montant");
        amountCol.setCellValueFactory(cellData -> {
            Double amount = cellData.getValue().getAmount();
            return new SimpleStringProperty(amount + " €");
        });
        TableColumn<Booking, String> isPaidCol = new TableColumn<>("Déjà payé?");
        isPaidCol.setCellValueFactory(cellData -> {
            Boolean isPaid = cellData.getValue().getIsPaid();
            return new SimpleStringProperty(isPaid ? "Oui" : "Non");
        });
        TableColumn<Booking, String> charityCol = new TableColumn<>("Association");
        charityCol.setCellValueFactory(cellData -> {
            String charity = cellData.getValue().getCharity().getName();
            return new SimpleStringProperty(charity);
        });
        TableColumn<Booking, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            return new SimpleStringProperty(date.format(formatter));
        });
        TableColumn<Booking, String> sessionCol = new TableColumn<>("Session");
        sessionCol.setCellValueFactory(cellData -> {
            LocalTime startHour = cellData.getValue().getSession().getStartHour();
            LocalTime endHour = cellData.getValue().getSession().getEndHour();
            String dayOfWeek = cellData.getValue().getSession().getDayOfWeek();
    
            return new SimpleStringProperty(dayOfWeek + " de " + startHour + " à " + endHour);
        });
        
        TableColumn<Booking, String> activityCol = new TableColumn<>("Activité");
        activityCol.setCellValueFactory(cellData -> {
            String title = cellData.getValue().getSession().getActivity().getTitle();
            return new SimpleStringProperty(title);
        });
        
        center.getColumns().setAll(firstnameCol, lastnameCol, phoneCol, emailCol,amountCol, isPaidCol, charityCol, dateCol, sessionCol, activityCol);
        pane.setCenter(center);
        
        getBookings();
    
        // border pane
        //vBox.setAlignment(Pos.CENTER);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    void getBookings() {
        try {
            System.out.println("Hello");
            ArrayList<Booking> charities = bookingController.getBookings();
            center.setItems(FXCollections.observableArrayList(charities));
        } catch (GetBookingsException e) {
            e.printStackTrace();
        }
    }
}
