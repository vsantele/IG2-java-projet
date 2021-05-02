package data.access;

import exception.data.*;
import model.*;

import java.util.ArrayList;

public interface BookingDataAccess {
  int addBooking(Booking booking) throws AddBookingException;
  int updateBooking(Booking booking) throws UpdateBookingException;
  ArrayList<Date> getDates(Session session);
  
  int deleteBooking(Booking booking) throws DeleteBookingException;
}
