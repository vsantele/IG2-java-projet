package view.component;

import javafx.scene.control.ListCell;
import model.Session;


public class SessionCell extends ListCell<Session> {
  
  @Override
  protected void updateItem(Session item, boolean empty) {
    super.updateItem(item, empty);
    setText(null);
    if(!empty && item != null) {
      String day = item.getDayOfWeek();
      String startHour = item.getStartHour().toString();
      String endHour = item.getEndHour().toString();
      final String text = String.format("%s de %s à %s", day, startHour, endHour);
      setText(text);
    }
  }
}
