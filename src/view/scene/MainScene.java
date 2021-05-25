package view.scene;

import business.BookingManager;
import business.DateGenerator;
import controller.BookingController;
import exception.data.DeleteBookingException;
import exception.data.GetException;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Booking;
import thread.TotalThread;
import view.stage.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class MainScene extends Scene {
    
    private BorderPane pane;
    private HBox hBoxTop;
    private TableView<Booking> center;
    
    private BookingController bookingController;
    
    private Form form;
    private AmountsPerActivitySearch amountsPerActivitySearch;
    private PeoplePerActivityAndCharitySearch peoplePerActivityAndCharitySearch;
    private CharityAtHourSearch charityAtHourSearch;
    
    private TotalThread totalThread;
    
    private Button addBtn;
    private Button amountsPerActivitySearchBtn;
    private Button peoplePerActivityAndCharitySearchBtn;
    private Button charityAtHourSearchBtn;
    
    private Alert choiceAlert;
    private Alert confirmAlert;
    private Alert errorAlert;
    private Alert infoAlert;
    
    private ButtonType editBtn;
    private ButtonType deleteBtn;
    private ButtonType cancelBtn;
    private ButtonType yesBtn;
    private ButtonType noBtn;
    
    public MainScene(Stage primaryStage, BookingController bookingController) {
        super(new BorderPane());
        pane = (BorderPane) this.getRoot();
        this.bookingController = bookingController;
    
        form = new Form(primaryStage, bookingController);
        
        amountsPerActivitySearch = new AmountsPerActivitySearch(primaryStage, bookingController);
        peoplePerActivityAndCharitySearch = new PeoplePerActivityAndCharitySearch(primaryStage, bookingController);
        charityAtHourSearch = new CharityAtHourSearch(primaryStage, bookingController);
    
        infoAlert = new Alert(Alert.AlertType.INFORMATION);
    
        choiceAlert = new Alert(Alert.AlertType.CONFIRMATION);
        choiceAlert.setTitle("Editer ou Supprimer");
        choiceAlert.setHeaderText("Quelle action voulez-vous effectuer?");
    
        errorAlert = new Alert(Alert.AlertType.ERROR);
    
        editBtn = new ButtonType("Editer");
        deleteBtn = new ButtonType("Supprimer");
        cancelBtn = new ButtonType("Annuler");
    
        choiceAlert.getButtonTypes().setAll(editBtn, deleteBtn, cancelBtn);
    
        confirmAlert = new Alert(Alert.AlertType.WARNING);
        confirmAlert.setTitle("Confirmation");
    
        yesBtn = new ButtonType("Oui");
        noBtn = new ButtonType("Non");
    
        confirmAlert.getButtonTypes().setAll(yesBtn, noBtn);
    
    
        primaryStage.setTitle("Application");
        primaryStage.centerOnScreen();
        primaryStage.setWidth(1300);
        primaryStage.setHeight(700);
    
        addBtn = new Button("Ajouter Réservation");
        addBtn.setOnAction(event -> {
            form.setUpdate(false);
            form.setBooking(null);
            form.showAndWait();
            getBookings();
        
        });
        amountsPerActivitySearchBtn = new Button("Recettes par activité");
        amountsPerActivitySearchBtn.setOnAction(event -> {
            amountsPerActivitySearch.reset();
            amountsPerActivitySearch.show();
        });
        peoplePerActivityAndCharitySearchBtn = new Button("Réservation pour activité et Association");
        peoplePerActivityAndCharitySearchBtn.setOnAction(event -> {
            peoplePerActivityAndCharitySearch.reset();
            peoplePerActivityAndCharitySearch.show();
        });
        charityAtHourSearchBtn = new Button("Association pour une heure et date");
        charityAtHourSearchBtn.setOnAction(event -> {
            charityAtHourSearch.reset();
            charityAtHourSearch.show();
        });
        hBoxTop = new HBox(30, addBtn, amountsPerActivitySearchBtn, peoplePerActivityAndCharitySearchBtn, charityAtHourSearchBtn);
        hBoxTop.setPadding(new Insets(15, 12, 15, 12));
        hBoxTop.setStyle("-fx-background-color: #336699;");
        pane.setTop(hBoxTop);
    
        center = new TableView<>();
    
        center.setRowFactory(tv -> {
            TableRow<Booking> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() >= 2 && !row.isEmpty()) {
                    Booking booking = row.getItem();
                    if (booking.getDate().isBefore(LocalDate.now())) {
                        infoAlert.setHeaderText("Trop tard");
                        infoAlert.setContentText("Vous ne pouvez pas modifier une réservation pour une date déjà passée");
                        infoAlert.show();
                    } else {
                        Optional<ButtonType> choice = choiceAlert.showAndWait();
                        if (choice.isPresent()) {
                            if (choice.get() == editBtn) {
                                form.setBooking(booking);
                                form.setUpdate(true);
                                form.showAndWait();
                                center.refresh();
                            } else if (choice.get() == deleteBtn) {
                                try {
                                    Optional<ButtonType> confirm = confirmAlert.showAndWait();
                                    if (confirm.isPresent()) {
                                        if (confirm.get() == yesBtn) {
                                            bookingController.deleteBooking(booking);
                                            getBookings();
                                        }
                                    }
                                } catch (DeleteBookingException e) {
                                    errorAlert.setHeaderText(null);
                                    errorAlert.setContentText(e.getMessage());
                                    errorAlert.show();
                                }
                            }
                        }
                    }
                }
            });
            return row;
        });
    
        TableColumn<Booking, String> firstnameCol = new TableColumn<>("Prénom");
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        TableColumn<Booking, String> lastnameCol = new TableColumn<>("Nom");
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    
        TableColumn<Booking, String> phoneCol = new TableColumn<>("Téléphone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TableColumn<Booking, String> birthdateCol = new TableColumn<>("Date de Naissance");
        birthdateCol.setCellValueFactory(cellData -> {
            LocalDate birthdate = cellData.getValue().getBirthdate();
            if (birthdate != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
                return new SimpleStringProperty(birthdate.format(formatter));
            }
            return new SimpleStringProperty("");
        });
        TableColumn<Booking, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Booking, String> amountCol = new TableColumn<>("Montant");
        amountCol.setCellValueFactory(cellData -> {
            Double amount = cellData.getValue().getAmount();
            return new SimpleStringProperty(String.format("%.2f", amount) + " €");
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
    
        getBookings();
        center.getColumns().setAll(firstnameCol, lastnameCol, phoneCol, birthdateCol, emailCol, amountCol, isPaidCol, charityCol, dateCol, sessionCol, activityCol);
        pane.setCenter(center);
        
    }
    
    void getBookings() {
        try {
            ArrayList<Booking> charities = bookingController.getBookings();
            center.setItems(FXCollections.observableArrayList(charities));
        } catch (GetException e) {
            errorAlert.setHeaderText(e.getMessage());
            errorAlert.setContentText(e.getDetails());
            errorAlert.show();
        }
    }
}
