package business;

import data.access.*;
import model.*;
import model.Date;

import java.util.*;

public class DateGenerator {
  static ArrayList<Date> getDate(Session session) {
    BookingDataAccess dao = new BookingDBAccess();
  
    ArrayList<Date> dates = dao.getDates(session);
    
    // TODO : Générer les dates si besoin + retirer les canceled si besoins
    
    return dates;
  }
}
