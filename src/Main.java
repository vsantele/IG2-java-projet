import business.BookingManager;
import controller.BookingController;
import data.access.*;
import exception.data.GetBookingsException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import util.Utils;
import view.*;
import view.stage.Form;

import java.util.ArrayList;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage){
        BookingManager bookingManager = new BookingManager();
        primaryStage.setTitle("Application");
        primaryStage.centerOnScreen();
        primaryStage.setWidth(800);
        primaryStage.setHeight(500);

        Label labelTop = new Label("Booking Table");
        Button buttonAdd = new Button("Add");
        Button buttonResearch1 = new Button("Research 1");
        Button buttonResearch2 = new Button("Research 2");
        Button buttonResearch3 = new Button("Research 3");
        HBox hBoxTop = new HBox(labelTop, buttonAdd, buttonResearch1, buttonResearch2, buttonResearch3);

        int nbRows = 10;
        HBox[] rows = new HBox[nbRows];

        for(int i = 0; i < nbRows; i++){
            Label labelRow = new Label("Row Infos");
            Button buttonEdit = new Button("Edit");
            Button buttonDelete = new Button("Delete");
            HBox row = new HBox(labelRow, buttonEdit, buttonDelete);
            rows[i] = row;
        }
        VBox vBoxTable = new VBox(rows);
        VBox vBox = new VBox(hBoxTop, vBoxTable);
        Pane pane = new Pane(vBox);
        pane.isVisible();
        Scene scene = new Scene(pane);
        // border pane
        //vBox.setAlignment(Pos.CENTER);
        primaryStage.setScene(scene);
        primaryStage.show();
        BookingController bookingController = new BookingController(bookingManager);
        Form form = new Form(primaryStage, bookingController);
        try {
            ArrayList<Booking> bookings = bookingManager.getBookings();
//            form.setBooking(bookings.get(0));
//            form.setUpdate(true);
            form.show();
        } catch (GetBookingsException e) {
            e.printStackTrace();
        }
    }
}
