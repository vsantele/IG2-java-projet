package view.component;

import javafx.scene.control.ListCell;
import model.Activity;


public class ActivityCell  extends ListCell<Activity> {
  
  @Override
  protected void updateItem(Activity item, boolean empty) {
    super.updateItem(item, empty);
    setText(null);
    if(!empty && item != null) {
      final String text = item.getTitle();
      setText(text);
    }
  }
}
