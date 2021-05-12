package business;

import data.access.*;
import exception.data.*;
import model.*;

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
}
