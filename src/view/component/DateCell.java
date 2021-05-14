package view.component;

import javafx.scene.control.ListCell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateCell extends ListCell<LocalDate>
{
  
  @Override
  protected void updateItem(LocalDate item, boolean empty) {
    super.updateItem(item, empty);
    setText(null);
    if(!empty && item != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
      final String text = item.format(formatter);
      setText(text);
    }
  }
}
