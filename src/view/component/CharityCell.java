package view.component;

import javafx.scene.control.ListCell;
import model.Charity;


public class CharityCell extends ListCell<Charity> {
  
  @Override
  protected void updateItem(Charity item, boolean empty) {
    super.updateItem(item, empty);
    setText(null);
    if(!empty && item != null) {
      final String text = item.getName();
      setText(text);
    }
  }
}
