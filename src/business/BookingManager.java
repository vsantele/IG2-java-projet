package business;

import data.access.*;
import exception.*;
import model.*;

public class BookingManager {
  private BookingDataAccess dao;
  
  public BookingManager() {
    this(new BookingDBAccess());
  }
  
  public BookingManager(BookingDataAccess dao) {
    this.dao = dao;
  }
  
  public int addBooking(Booking booking) throws AddBookingException {
    return dao.addBooking(booking);
  }
  
  public int updateBooking(Booking booking) throws UpdateBookingException {
    return dao.updateBooking(booking);
  }
}
