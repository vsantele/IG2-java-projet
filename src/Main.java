import data.access.*;
import model.Charity;

import java.sql.SQLException;

public class Main {
  public static void main(String[] args) {
      BookingDataAccess dao = new BookingDBAccess();
    try {
      Charity charity = new Charity("avmp", "À Vos Marques Prêts", "contact@avmp.be","5, Rue des platanes", "Mouscron", "7700", "Belgique");
      int nb = dao.addCharity(charity);
      System.out.println(nb);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

  }
}
