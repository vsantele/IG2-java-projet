package data.access;

import exception.data.*;
import model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface BookingDataAccess {
  ArrayList<Booking> getBookings() throws GetBookingsException;
  ArrayList<Booking> getBookings(Session session, LocalDate date) throws GetBookingsException;
  int addBooking(Booking booking) throws AddBookingException;
  int updateBooking(Booking booking) throws UpdateBookingException;
  int deleteBooking(Booking booking) throws DeleteBookingException;
  
  ArrayList<Date> getDates(Session session, LocalDate start, LocalDate end) throws GetDatesException;
  ArrayList<Activity> getActivities() throws GetActivitiesException;
  ArrayList<Session> getSessions(Activity activity) throws GetSessionsException;
  ArrayList<Charity> getCharities() throws GetCharityException;
  
  void getAmountsPerActivity(Charity charity) throws GetAmountsPerActivityException;
  void getPeoplePerActivityAndCharity(Activity activity, Charity charity) throws GetPeoplePerActivityAndCharityException;
  void getCharityAtHour(LocalTime time) throws GetCharityAtHourException;
  
}
