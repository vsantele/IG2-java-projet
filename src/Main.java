import business.BookingManager;
import business.DateGenerator;
import controller.BookingController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import thread.TotalThread;
import view.scene.MainScene;
import view.stage.*;


public class Main extends Application {
  private BookingManager bookingManager;
  private DateGenerator dateGenerator;
  private BookingController bookingController;

  private Total total;
  
  private TotalThread totalThread;
  
  public static void main(String[] args) {
    launch(args);
  }
  
  @Override
  public void start(Stage primaryStage) {
    bookingManager = new BookingManager();
    dateGenerator = new DateGenerator();
    bookingController = new BookingController(bookingManager, dateGenerator);
    total = new Total(primaryStage, bookingController);
    totalThread = new TotalThread(total);
    totalThread.start();

    Scene mainScene = new MainScene(primaryStage, bookingController);
    primaryStage.setScene(mainScene);
    primaryStage.show();
    total.show();
    primaryStage.setOnCloseRequest(event -> totalThread.interrupt());
  }
}
