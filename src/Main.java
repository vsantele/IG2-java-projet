import data.access.*;
import model.*;

import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
      BookingDataAccess dao = new BookingDBAccess();
      Session ses = new Session(5);
      ArrayList<Date> arr = dao.getDates(ses);
      for(Date date : arr) {
        System.out.println(date);
      }
  }
}
