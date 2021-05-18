package controller;

import business.BookingManager;
import exception.data.*;
import model.Activity;
import model.Booking;
import model.Charity;
import model.Session;

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
    try {
      if (!bookingManager.isSessionFull(booking.getSession(), booking.getDate())) {
        bookingManager.addBooking(booking);
        return true;
      }
    } catch (IsSessionFullException e) {
      throw new AddBookingException(e.getMessage());
    }
    return false;
  }
  
  public Boolean updateBooking(Booking booking) throws UpdateBookingException {
    try {
      if (!bookingManager.isSessionFull(booking.getSession(),booking.getDate())) {
        bookingManager.updateBooking(booking);
        return true;
      }
    } catch (IsSessionFullException e) {
      throw new UpdateBookingException(e.getMessage());
    }
    return false;
  }
  
}
