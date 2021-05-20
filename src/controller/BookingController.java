package controller;

import business.BookingManager;
import exception.data.*;
import model.*;

import java.time.LocalTime;
import java.util.ArrayList;

public class BookingController {
  private BookingManager bookingManager;
  
  public BookingController(BookingManager bookingManager) {
    this.bookingManager = bookingManager;
  }
  
  public Activity getActivity(Session session) throws GetActivityException {
    return getActivity(session.getId());
  }
  
  public Activity getActivity(Integer sessionId) throws GetActivityException {
    return bookingManager.getActivity(sessionId);
  }
  
  public ArrayList<Session> getSessions(Activity activity) throws GetSessionsException {
    return bookingManager.getSessions(activity);
  }
  
  public ArrayList<Activity> getActivities() throws GetActivitiesException {
    return bookingManager.getActivities();
  }
  
  public Charity getCharity(String charityCode) throws GetCharityException {
    return bookingManager.getCharity(charityCode);
  }
  
  public ArrayList<Charity> getCharities() throws GetCharityException {
    return bookingManager.getCharities();
  }
  
  public Boolean addBooking(Booking booking) throws AddBookingException {
    return bookingManager.addBooking(booking);
  }
  
  public Boolean updateBooking(Booking booking) throws UpdateBookingException {
    return bookingManager.updateBooking(booking);
  }
  
  public ArrayList<AmountActivity> getAmountsPerActivity(Charity charity) throws GetAmountsPerActivityException {
    return bookingManager.getAmountsPerActivity(charity);
  }
  
  public ArrayList<Booking> getPeoplePerActivityAndCharity(Activity activity, Charity charity) throws GetPeoplePerActivityAndCharityException {
    return bookingManager.getPeoplePerActivityAndCharity(activity, charity);
  }
  
  public ArrayList<Charity> getCharityAtHour(LocalTime time) throws GetCharityAtHourException {
    return bookingManager.getCharityAtHour(time);
  }
  
  public ArrayList<Booking> getBookings() throws GetBookingsException {
    return bookingManager.getBookings();
  }
}
