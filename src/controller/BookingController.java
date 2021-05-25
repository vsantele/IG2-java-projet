package controller;

import business.BookingManager;
import business.DateGenerator;
import exception.business.InvalidDatesException;
import exception.data.*;
import model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class BookingController {
  private final BookingManager bookingManager;
  private final DateGenerator dateGenerator;
  
  public BookingController(BookingManager bookingManager, DateGenerator dateGenerator) {
    this.bookingManager = bookingManager;
    this.dateGenerator = dateGenerator;
  }
  
  public Activity getActivity(Session session) throws GetException {
    return getActivity(session.getId());
  }
  
  public Activity getActivity(Integer sessionId) throws GetException {
    return bookingManager.getActivity(sessionId);
  }
  
  public ArrayList<Session> getSessions(Activity activity) throws GetException {
    return bookingManager.getSessions(activity);
  }
  
  public ArrayList<Activity> getActivities() throws GetException {
    return bookingManager.getActivities();
  }
  
  public Charity getCharity(String charityCode) throws GetException {
    return bookingManager.getCharity(charityCode);
  }
  
  public ArrayList<Charity> getCharities() throws GetException {
    return bookingManager.getCharities();
  }
  
  public Boolean addBooking(Booking booking) throws AddBookingException {
    return bookingManager.addBooking(booking);
  }
  
  public Boolean updateBooking(Booking booking) throws UpdateBookingException {
    return bookingManager.updateBooking(booking);
  }
  
  public int deleteBooking(Booking booking) throws DeleteBookingException {
    return bookingManager.deleteBooking(booking);
  }
  
  public ArrayList<AmountActivity> getAmountsPerActivity(Charity charity) throws GetException {
    return bookingManager.getAmountsPerActivity(charity);
  }
  
  public ArrayList<Booking> getPeoplePerActivityAndCharity(Activity activity, Charity charity) throws GetException {
    return bookingManager.getPeoplePerActivityAndCharity(activity, charity);
  }
  
  public ArrayList<Charity> getCharityAtHour(LocalTime time) throws GetException {
    return bookingManager.getCharityAtHour(time);
  }
  
  public ArrayList<Booking> getBookings() throws GetException {
    return bookingManager.getBookings();
  }
  
  public Double getTotal() throws GetException {
    return bookingManager.getTotal();
  }
  
  public ArrayList<LocalDate> getDates(Session session, LocalDate start, LocalDate end) throws GetException, InvalidDatesException {
    ArrayList<Date> sessionDates = bookingManager.getDates(session, start, end);
    return dateGenerator.getDates(session.isWeekly(), session.getNumDay(), start, end, sessionDates);
  }
  
  public ArrayList<LocalDate> getDates(Session session, LocalDate start) throws GetException, InvalidDatesException {
    LocalDate later = start.plusDays(14);
    return getDates(session, start, later);
  }
  
  public ArrayList<LocalDate> getDates(Session session) throws GetException, InvalidDatesException {
    LocalDate start = LocalDate.now();
    return getDates(session, start);
  }
  
}
