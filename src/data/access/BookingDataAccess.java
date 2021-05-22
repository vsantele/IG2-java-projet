package data.access;

import exception.data.*;
import model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface BookingDataAccess {
  ArrayList<Booking> getBookings() throws GetException;
  ArrayList<Booking> getBookings(Session session, LocalDate date) throws GetException;
  int addBooking(Booking booking) throws AddBookingException;
  int updateBooking(Booking booking) throws UpdateBookingException;
  int deleteBooking(Booking booking) throws DeleteBookingException;
  
  ArrayList<Date> getDates(Session session, LocalDate start, LocalDate end) throws GetException;
  ArrayList<Activity> getActivities() throws GetException;
  ArrayList<Session> getSessions(Activity activity) throws GetException;
  ArrayList<Charity> getCharities() throws GetException;
  
  ArrayList<AmountActivity> getAmountsPerActivity(Charity charity) throws GetException;
  ArrayList<Booking> getPeoplePerActivityAndCharity(Activity activity, Charity charity) throws GetException;
  ArrayList<Charity> getCharityAtHour(LocalTime time) throws GetException;
  
  Activity getActivity(Integer session) throws GetException;
  Charity getCharity(String charityCode) throws GetException;
  Boolean isSessionFull(Session session, LocalDate date) throws GetException;
  
  Double getTotal() throws GetException;
}
