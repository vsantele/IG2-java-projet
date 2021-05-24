package business;

import exception.business.InvalidDatesException;
import model.Date;

import java.time.*;
import java.util.*;

public class DateGenerator {
  
  public DateGenerator() {
  }

  public ArrayList<LocalDate> getDates(Boolean isWeekly, DayOfWeek sessionDayOfWeek, LocalDate start, LocalDate end, ArrayList<Date> sessionDates) throws InvalidDatesException {
    if (start.isAfter(end)) throw new InvalidDatesException(start, end);
    ArrayList<LocalDate> dates = new ArrayList<>();
    
    if (isWeekly) {
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
}
