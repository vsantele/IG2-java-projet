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
    if (booking == null) throw new AddBookingException("Réservation invalide");
    try {
      if (!dao.isSessionFull(booking.getSession(), booking.getDate())) {
        int index = dao.addBooking(booking);
        booking.setId(index);
        return true;
      }
    } catch (GetException e) {
      throw new AddBookingException("Erreur lors de la vérification des places disponibles");
    }
    return false;
  }
  
  public Boolean updateBooking(Booking booking) throws UpdateBookingException {
    if (booking == null || booking.getId() == null) throw new UpdateBookingException("Réservation non identifiable");
    try {
      if (!isSessionFull(booking.getSession(),booking.getDate())) {
        dao.updateBooking(booking);
        return true;
      }
    } catch (GetException e) {
      throw new UpdateBookingException("Erreur lors de la vérification des places disponibles");
    }
    return false;
  }
  
  public int deleteBooking(Booking booking) throws DeleteBookingException {
    if (booking == null || booking.getId() == null) throw new DeleteBookingException("Réservation non identifiable");
    return dao.deleteBooking(booking);
  }
  
  public ArrayList<Booking> getBookings() throws GetException {
    return dao.getBookings();
  }
  
  public ArrayList<Session> getSessions(Activity activity) throws GetException {
    if (activity == null || activity.getCode() == null) throw new GetException("Activité non identifiable");
    return dao.getSessions(activity);
  }
  
  public ArrayList<Activity> getActivities() throws GetException {
    return dao.getActivities();
  }
  
  public Charity getCharity(String charityCode) throws GetException {
    if (charityCode == null ) throw new GetException("Association non identifiable");
    return dao.getCharity(charityCode);
  }
  
  public ArrayList<Charity> getCharities() throws GetException {
    return dao.getCharities();
  }
  
  public Boolean isSessionFull(Session session, LocalDate date) throws GetException {
    if (session == null || session.getId() == null ) throw new GetException("Session non identifiable");
    if (date == null) throw new GetException("Date non valide");
    return dao.isSessionFull(session, date);
  }
  
  public ArrayList<Charity> getCharityAtHour(LocalTime time) throws GetException {
    if (time == null) throw new GetException("Heure non valide");
    return dao.getCharityAtHour(time);
  }
  
  public ArrayList<Booking> getPeoplePerActivityAndCharity(Activity activity, Charity charity) throws GetException {
    if (activity == null || activity.getCode() == null) throw new GetException("Activité non identifiable");
    if (charity == null || charity.getCode() == null) throw new GetException("Association non identifiable");
    return dao.getPeoplePerActivityAndCharity(activity, charity);
  }
  
  public ArrayList<AmountActivity> getAmountsPerActivity(Charity charity) throws GetException {
    if (charity == null || charity.getCode() == null) throw new GetException("Association non identifiable");
    return dao.getAmountsPerActivity(charity);
  }
  
  public Activity getActivity(Integer session) throws GetException {
    if (session == null) throw new GetException("Session non valide");
    return dao.getActivity(session);
  }
  
  public Double getTotal() throws GetException {
    return dao.getTotal();
  }
}
