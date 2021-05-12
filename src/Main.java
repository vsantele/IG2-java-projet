import data.access.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import view.*;

import java.util.ArrayList;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Application");
        primaryStage.centerOnScreen();
        primaryStage.setWidth(800);
        primaryStage.setHeight(500);
        primaryStage.show();

        VBox vBox = new VBox(new Label("Booking Table"));
        Scene scene = new Scene(vBox);
        // border pane
        //vBox.setAlignment(Pos.CENTER);
        primaryStage.setScene(scene);


    }
}
