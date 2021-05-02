package business;

import data.access.*;
import model.*;
import model.Date;

import java.time.*;
import java.util.*;
import java.util.stream.Stream;

public class DateGenerator {
  static public ArrayList<LocalDate> getDates(Session session, LocalDate start, LocalDate end) {
    System.out.println("Start: " + start);
    System.out.println("End: " + end);
    BookingDataAccess dao = new BookingDBAccess();
    ArrayList<LocalDate> dates = new ArrayList<>();
  
    ArrayList<Date> sessionDates = dao.getDates(session);
    
    // TODO : Générer les dates si besoin + retirer les canceled si besoins
    if (session.getWeekly()) {
      DayOfWeek sessionDayOfWeek = session.getNumDay();
      DayOfWeek startDayOfWeek = start.getDayOfWeek();
      // TODO: Fix si changement de semaine
      int diffDay = sessionDayOfWeek.getValue() - startDayOfWeek.getValue();
      if (diffDay < 0) {
        diffDay += 7;
      }
      LocalDate calculatedDate = start.plusDays(diffDay);
      while (!calculatedDate.isAfter(end) ) {
        dates.add(calculatedDate);
        calculatedDate = calculatedDate.plusDays(7);
      }
    } else {
      sessionDates.stream().filter(date -> date.getType().equals("custom")).forEach(date -> dates.add(date.getDate()));
    }
    dates.removeIf(date -> sessionDates.stream().filter(in -> in.getType().equals("canceled")).map(Date::getDate).anyMatch(in -> in.isEqual(date)));
    
    return dates;
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
