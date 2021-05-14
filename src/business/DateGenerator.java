package business;

import data.access.*;
import exception.data.GetDatesException;
import model.*;
import model.Date;

import java.time.*;
import java.util.*;

public class DateGenerator {
  static public ArrayList<LocalDate> getDates(Session session, LocalDate start, LocalDate end) throws GetDatesException {
    BookingDataAccess dao = new BookingDBAccess();
    ArrayList<LocalDate> dates = new ArrayList<>();
  
    ArrayList<Date> sessionDates = dao.getDates(session, start, end);

    if (session.isWeekly()) {
      DayOfWeek sessionDayOfWeek = session.getNumDay();
      DayOfWeek startDayOfWeek = start.getDayOfWeek();
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
      sessionDates.stream().filter(date -> date.getType().equals(Date.CUSTOM)).forEach(date -> dates.add(date.getDate()));
    }
    dates.removeIf(date -> sessionDates.stream().filter(in -> in.getType().equals(Date.CANCELED)).map(Date::getDate).anyMatch(in -> in.isEqual(date)));
    
    return dates;
  }
  
   static public ArrayList<LocalDate> getDates(Session session) throws GetDatesException {
    LocalDate start = LocalDate.now();
    return getDates(session, start);
  }
  
  static public ArrayList<LocalDate> getDates(Session session, LocalDate start) throws GetDatesException {
    LocalDate later = start.plusDays(14);
    return getDates(session, start, later);
  }
}
