import business.BookingManager;
import controller.BookingController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.stage.Form;
import view.stage.Search1;
import view.stage.Search2;
import view.stage.Search3;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage){
        BookingManager bookingManager = new BookingManager();
        BookingController bookingController = new BookingController(bookingManager);
        Form form = new Form(primaryStage, bookingController);
        Search1 search1 = new Search1(primaryStage, bookingController);
        Search2 search2 = new Search2(primaryStage, bookingController);
        Search3 search3 = new Search3(primaryStage, bookingController);
        primaryStage.setTitle("Application");
        primaryStage.centerOnScreen();
        primaryStage.setWidth(800);
        primaryStage.setHeight(500);

        Label labelTop = new Label("Booking Table");
        Button buttonAdd = new Button("Add");
        buttonAdd.setOnAction(event -> {
            form.setBooking(null);
            form.show();
        });
        Button buttonResearch1 = new Button("Research 1");
        buttonResearch1.setOnAction(event -> {
            search1.show();
        });
        Button buttonResearch2 = new Button("Research 2");
        buttonResearch2.setOnAction(event -> {
            search2.show();
        });
        Button buttonResearch3 = new Button("Research 3");
        buttonResearch3.setOnAction(event -> {
            search3.show();
        });
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
    }
}
