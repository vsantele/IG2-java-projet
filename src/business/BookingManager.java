package business;

import data.access.*;
import exception.data.*;
import model.*;

import java.time.LocalDate;
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
  
  public Boolean addBooking(Booking booking) throws AddBookingException {
    try {
      if (!dao.isSessionFull(booking.getSession(), booking.getDate())) {
        int index = dao.addBooking(booking);
        booking.setId(index);
        return true;
      }
    } catch (IsSessionFullException e) {
      throw new AddBookingException(e.getMessage());
    }
    return false;
  }
  
  public Boolean updateBooking(Booking booking) throws UpdateBookingException {
    try {
      if (!dao.isSessionFull(booking.getSession(),booking.getDate())) {
        dao.updateBooking(booking);
        return true;
      }
    } catch (IsSessionFullException e) {
      throw new UpdateBookingException(e.getMessage());
    }
    return false;
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
  
  public Charity getCharity(String charityCode) throws GetCharityException {
    return dao.getCharity(charityCode);
  }
  
  public ArrayList<Charity> getCharities() throws GetCharityException {
    return dao.getCharities();
  }
  
  public Boolean isSessionFull(Session session, LocalDate date) throws IsSessionFullException {
    return dao.isSessionFull(session, date);
  }
  
  public ArrayList<Charity> getCharityAtHour(LocalTime time) throws GetCharityAtHourException {
    return dao.getCharityAtHour(time);
  }
  
  public ArrayList<Booking> getPeoplePerActivityAndCharity(Activity activity, Charity charity) throws GetPeoplePerActivityAndCharityException {
    return dao.getPeoplePerActivityAndCharity(activity, charity);
  }
  
  public ArrayList<AmountActivity> getAmountsPerActivity(Charity charity) throws GetAmountsPerActivityException {
    return dao.getAmountsPerActivity(charity);
  }
  
  public Activity getActivity(Integer session) throws GetActivityException {
    return dao.getActivity(session);
  }
}
