package controller;

import business.BookingManager;
import exception.data.*;
import model.*;

import java.time.LocalTime;
import java.util.ArrayList;

public class BookingController {
  private final BookingManager bookingManager;
  
  public BookingController(BookingManager bookingManager) {
    this.bookingManager = bookingManager;
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
}
