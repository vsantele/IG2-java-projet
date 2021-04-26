package data.access;

import exception.AddBookingException;
import exception.UpdateBookingException;
import model.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingDataAccess {
  int addBooking(Booking booking) throws AddBookingException;
  int updateBooking(Booking booking) throws UpdateBookingException;
  ArrayList<Date> getDates(Session session);
}
