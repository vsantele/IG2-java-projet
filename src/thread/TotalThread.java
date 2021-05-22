package thread;

import javafx.stage.Stage;
import view.stage.Total;

public class TotalThread extends Thread {
  private Total stage;
  
  public TotalThread(Total stage) {
    super("total");
    this.stage = stage;
  }
  
  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      stage.update();
    }
  }
}
