package business;

import data.access.*;
import model.*;
import model.Date;

import java.time.*;
import java.util.*;

public class DateGenerator {
  static public ArrayList<LocalDate> getDates(Session session, LocalDate start, LocalDate end) {
    
    BookingDataAccess dao = new BookingDBAccess();
    ArrayList<LocalDate> sessionDates = new ArrayList<>();
  
    ArrayList<Date> dates = dao.getDates(session);
    
    // TODO : Générer les dates si besoin + retirer les canceled si besoins
    if (session.getWeekly()) {
      DayOfWeek sessionDayOfWeek = session.getNumDay();
      DayOfWeek startDayOfWeek = start.getDayOfWeek();
      // TODO: Fix si changement de semaine
      int diffDay = Math.abs(sessionDayOfWeek.getValue() - startDayOfWeek.getValue());
      LocalDate date = start.plusDays(diffDay);
      while (end.isAfter(date)) {
        sessionDates.add(date);
        date = date.plusDays(7);
      }
    }
    
    return sessionDates;
  }
  
   static public ArrayList<LocalDate> getDates(Session session) {
    LocalDate start = LocalDate.now();
    return getDates(session, start);
  }
  
  static public ArrayList<LocalDate> getDates(Session session, LocalDate start) {
    LocalDate later = start.plusDays(14);
    return getDates(session, start, later);
  }
}
