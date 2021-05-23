package thread;

import controller.BookingController;
import exception.data.GetException;
import view.stage.Total;

public class TotalThread extends Thread {
  private final Total stage;
  private Boolean hasError;
  
  public TotalThread(Total stage, BookingController controller) {
    super("total");
    this.stage = stage;
    hasError = false;
  }
  
  @Override
  public void run() {
    while (!hasError) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      try {
        stage.update();
      } catch (GetException e) {
        hasError = true;
      }
    }
  }
}
