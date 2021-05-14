package business;

import data.access.*;
import exception.data.*;
import model.*;

import java.time.LocalTime;
import java.util.ArrayList;

public class BookingManager {
  private BookingDataAccess dao;
  
  public BookingManager() {
    this(new BookingDBAccess());
  }
  
  public BookingManager(BookingDataAccess dao) {
    this.dao = dao;
  }
  
  public Booking addBooking(Booking booking) throws AddBookingException {
    int index = dao.addBooking(booking);
    booking.setId(index);
    return booking;
  }
  
  public int updateBooking(Booking booking) throws UpdateBookingException {
    return dao.updateBooking(booking);
  }
  
  public int deleteBooking(Booking booking) throws DeleteBookingException {
    return dao.deleteBooking(booking);
  }
  
  public ArrayList<Booking> getBookings() throws GetBookingsException {
    return dao.getBookings();
  }
  
  public ArrayList<Session> getSessions(Activity activity) throws GetSessionsException {
    return dao.getSessions(activity);
  }
  
  public ArrayList<Activity> getActivities() throws GetActivitiesException {
    return dao.getActivities();
  }
  
  public ArrayList<Charity> getCharities() throws GetCharityException {
    return dao.getCharities();
  }
  
  public ArrayList<String> getCharityAtHour(LocalTime time) throws GetCharityAtHourException {
    return dao.getCharityAtHour(time);
  }
  
  public ArrayList<Booking> getPeoplePerActivityAndCharity(Activity activity, Charity charity) throws GetPeoplePerActivityAndCharityException {
    return dao.getPeoplePerActivityAndCharity(activity, charity);
  }
  
  public ArrayList<AmountActivity> getAmountsPerActivity(Charity charity) throws GetAmountsPerActivityException {
    return dao.getAmountsPerActivity(charity);
  }
}
