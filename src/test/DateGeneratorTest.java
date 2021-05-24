package test;

import business.BookingManager;
import business.DateGenerator;
import exception.business.InvalidDatesException;
import model.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DateGeneratorTest {
  private DateGenerator dateGenerator;
  
  @BeforeEach
  public void setUp() {
    dateGenerator = new DateGenerator();
  }
  
  @Test
  void getDates() throws InvalidDatesException {
    ArrayList<Date> sessionDates1 = new ArrayList<>();
    sessionDates1.add(new Date(Date.CANCELED, LocalDate.of(2021,5,31)));
    ArrayList<LocalDate> dates1 = new ArrayList<>();
    dates1.add(LocalDate.of(2021,5,24));
    dates1.add(LocalDate.of(2021,6,7));
    assertEquals(dates1, dateGenerator.getDates(true, DayOfWeek.MONDAY, LocalDate.of(2021,5,24), LocalDate.of(2021,6,7), sessionDates1));
    
    ArrayList<Date> sessionDates2 = new ArrayList<>();
    sessionDates2.add(new Date(Date.CUSTOM, LocalDate.of(2021,5,31)));
    ArrayList<LocalDate> dates2 = new ArrayList<>();
    dates2.add(LocalDate.of(2021,5,31));
    assertEquals(dates2, dateGenerator.getDates(false, DayOfWeek.MONDAY, LocalDate.of(2021,5,24), LocalDate.of(2021,6,7), sessionDates2));
  
    ArrayList<Date> sessionDates3 = new ArrayList<>();
    ArrayList<LocalDate> dates3 = new ArrayList<>();
    dates3.add(LocalDate.of(2021,5,24));
    dates3.add(LocalDate.of(2021,5,31));
    dates3.add(LocalDate.of(2021,6,7));
    assertEquals(dates3, dateGenerator.getDates(true, DayOfWeek.MONDAY, LocalDate.of(2021,5,24), LocalDate.of(2021,6,7), sessionDates3));
  
    ArrayList<Date> sessionDates4 = new ArrayList<>();
    
    assertThrows(InvalidDatesException.class, () -> dateGenerator.getDates(true, DayOfWeek.MONDAY, LocalDate.of(2021,5,25), LocalDate.of(2021,5,24), sessionDates4));
    
    ArrayList<Date> sessionDates5 = new ArrayList<>();
    ArrayList<LocalDate> dates5 = new ArrayList<>();
    dates5.add(LocalDate.of(2021,5,24));
    assertEquals(dates5, dateGenerator.getDates(true, DayOfWeek.MONDAY, LocalDate.of(2021,5,24), LocalDate.of(2021,5,24), sessionDates5));
  }

}