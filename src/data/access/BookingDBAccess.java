package data.access;

import data.connection.MariadbConnection;
import exception.SessionNumDayException;
import exception.data.*;
import model.*;
import model.Date;
import util.Utils;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class BookingDBAccess implements BookingDataAccess {
  private Connection connection;
  
  public BookingDBAccess() {
    connection = MariadbConnection.getInstance();
  }
  
  public int addBooking(Booking booking) throws AddBookingException {
    String sql = "INSERT INTO booking(last_name, first_name, amount, is_paid, phone, birth_date, email, date, charity_code, session_id) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?);";
    try {
      PreparedStatement req = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

      req.setString(1, booking.getLastname());
      req.setString(2, booking.getFirstname());
      req.setDouble(3, booking.getAmount());
      req.setBoolean(4, booking.getPaid());
      req.setString(5, booking.getPhone());

      java.sql.Date sqlBirthDate = Utils.toSqlDate(booking.getBirthdate());
      req.setDate(6, sqlBirthDate);

      if (booking.getEmail() == null) {
        req.setString(7, booking.getEmail());
      } else {
        req.setNull(7, Types.VARCHAR);
      }

      java.sql.Date sqlDate = Utils.toSqlDate(booking.getDate());
      req.setDate(8, sqlDate);

      req.setString(9, booking.getCharityCode());
      req.setInt(10, booking.getSessionId());

      int affectedRows = req.executeUpdate();

      if (affectedRows == 0) {
        throw new AddBookingException("Aucune ligne n'a été ajoutée");
      }

      ResultSet generatedKeys = req.getGeneratedKeys();
      if (generatedKeys.next()) {
        return generatedKeys.getInt(1);
      } else {
        throw new AddBookingException("Erreur lors de la récupération de l'id");
      }

    } catch (SQLException exception) {
      throw new AddBookingException(exception.getMessage());
    }
  }
  
  public int updateBooking(Booking booking) throws UpdateBookingException {
    String sql = "UPDATE booking set last_name = ?, first_name = ?, amount = ?, is_paid = ?, phone = ?, birth_date = ?, email = ?, date = ?, charity_code = ?, session_id = ?" +
            "WHERE booking_id = ?;";
    try {
      PreparedStatement req = connection.prepareStatement(sql);
  
      req.setString(1, booking.getLastname());
      req.setString(2, booking.getFirstname());
      req.setDouble(3, booking.getAmount());
      req.setBoolean(4, booking.getPaid());
      req.setString(5, booking.getPhone());
  
      java.sql.Date sqlBirthDate = Utils.toSqlDate(booking.getBirthdate());
      req.setDate(6, sqlBirthDate);
  
      if (booking.getEmail() == null) {
        req.setString(7, booking.getEmail());
      } else {
        req.setNull(7, Types.VARCHAR);
      }
  
      java.sql.Date sqlDate = Utils.toSqlDate(booking.getDate());
      
      req.setDate(8, sqlDate);
  
      req.setString(9, booking.getCharity().getCode());
      req.setInt(10, booking.getSession().getId());
      req.setInt(11, booking.getId());
      return req.executeUpdate();
    } catch (SQLException exception) {
      throw new UpdateBookingException(exception.getMessage());
    }
  }
  
  public int deleteBooking(Booking booking) throws DeleteBookingException {
    String sql = "DELETE FROM booking WHERE booking_id = ?";
  
    try {
      PreparedStatement req = connection.prepareStatement(sql);
      req.setInt(1, booking.getId());
      
      return req.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DeleteBookingException(e.getMessage());
    }
  }
  
  public ArrayList<Date> getDates(Session session) throws GetDatesException {
    ArrayList<Date> dates = new ArrayList<>();
    String sql = "SELECT date_id, date, type " +
            "FROM date " +
            "WHERE session_id = ?";
    
    try {
      PreparedStatement req = connection.prepareStatement(sql);
      
      req.setInt(1, session.getId());
      
      ResultSet data = req.executeQuery();
      ResultSetMetaData meta = data.getMetaData();
      while (data.next()) {
        Integer id = data.getInt("date_id");
        String type = data.getString("type");
        LocalDate date = data.getDate("date").toLocalDate();
        
        Date entryDate = new Date(id, type, date, session);
        dates.add(entryDate);
      }
    } catch (SQLException e) {
      throw new GetDatesException(e.getMessage());
    }
    
    return dates;
  }
  
  public ArrayList<Activity> getActivities() throws GetActivitiesException {
    ArrayList<Activity> activities = new ArrayList<>();
    String sql = "SELECT activity_code, title FROM activity;";
    
    try {
      PreparedStatement req = connection.prepareStatement(sql);
      
      ResultSet data = req.executeQuery();
      
      while(data.next()) {
        String activityCode = data.getString("activity_code");
        String title = data.getString("title");
        
        Activity activity = new Activity(activityCode, title);
        activities.add(activity);
      }
    } catch (SQLException e) {
      throw new GetActivitiesException(e.getMessage());
    }
    
    return activities;
  }
  
  public ArrayList<Session> getSessions(Activity activity) throws GetSessionsException {
    ArrayList<Session> sessions = new ArrayList<>();
    String sql = "SELECT session_id, num_day, start_hour, end_hour, is_weekly, nb_max " +
            "FROM session " +
            "WHERE activity_code = ?";
  
    try {
      PreparedStatement req = connection.prepareStatement(sql);
      
      req.setString(1, activity.getCode());
    
      ResultSet data = req.executeQuery();
      
      while(data.next()) {
        Integer sessionId = data.getInt("session_id");
        Integer numDay = data.getInt("num_day");
        LocalTime startHour = data.getTime("start_hour").toLocalTime();
        LocalTime endHour= data.getTime("end_hour").toLocalTime();
        Boolean isWeekly = data.getBoolean("is_weekly");
        Integer nbMax = data.getInt("nb_max");
        
        Session session = new Session(sessionId, numDay, startHour, endHour, isWeekly, nbMax, activity);
        sessions.add(session);
      }
    } catch (SQLException | SessionNumDayException e) {
      throw new GetSessionsException(e.getMessage());
    }
    
    return sessions;
  }
  
  public ArrayList<Charity> getCharities() throws GetCharityException{
    ArrayList<Charity> charities = new ArrayList<>();
    String sql = "SELECT charity_code, name, contact, address, city, country, zip_code FROM charity;";
  
    try {
      PreparedStatement req = connection.prepareStatement(sql);
    
      ResultSet data = req.executeQuery();
      
      while (data.next()) {
        String charityCode = data.getString("charity_code");
        String name = data.getString("name");
        String contact = data.getString("contact");
        String address = data.getString("address");
        String city = data.getString("city");
        String country = data.getString("country");
        String zip_code = data.getString("zip_code");
        
        Charity charity = new Charity(charityCode, name, contact, address, city, country, zip_code);
        charities.add(charity);
      }
    } catch (SQLException e) {
      throw new GetCharityException(e.getMessage());
    }
    
    return charities;
  }
  
  public ArrayList<Booking> getBookings() throws GetBookingsException {
    ArrayList<Booking> bookings = new ArrayList<>();
    String sql = "SELECT booking_id, last_name, first_name, amount, is_paid, phone, birth_date, email, date, charity_code, session_id FROM booking;";
  
    try {
      PreparedStatement req = connection.prepareStatement(sql);
    
      ResultSet data = req.executeQuery();
      
      while(data.next()) {
        Integer bookingId = data.getInt("booking_id");
        String lastName = data.getString("last_name");
        String firstName = data.getString("first_name");
        Double amount = data.getDouble("amount");
        Boolean isPaid = data.getBoolean("is_paid");
        String phone = data.getString("phone");
        java.sql.Date sqlBirthDate = data.getDate("birth_date");
        LocalDate birthdate = null;
        if (sqlBirthDate != null) {
          birthdate = sqlBirthDate.toLocalDate();
        }
        String email = data.getString("email");
        LocalDate date = data.getDate("date").toLocalDate();
        String charityCode = data.getString("charity_code");
        Integer sessionId = data.getInt("session_id");
        
        Booking booking = new Booking(bookingId, lastName, firstName, amount, isPaid, phone, birthdate, email, date, charityCode, sessionId);
        bookings.add(booking);
      }
    } catch (SQLException e) {
      throw new GetBookingsException(e.getMessage());
    }
    
    return bookings;
  }
  
  public ArrayList<Booking> getBookings(Session session, LocalDate date) throws GetBookingsException {
    ArrayList<Booking> bookings = new ArrayList<>();
    String sql = "SELECT booking_id, last_name, first_name, amount, is_paid, phone, birth_date, email, charity_code " +
            "FROM booking " +
            "WHERE session_id = ? AND date = ?;";
  
    try {
      PreparedStatement req = connection.prepareStatement(sql);
      
      req.setInt(1, session.getId());
      req.setDate(2, Utils.toSqlDate(date));
      
      ResultSet data = req.executeQuery();
      
      while (data.next()) {
        Integer bookingId = data.getInt("booking_id");
        String lastName = data.getString("last_name");
        String firstName = data.getString("first_name");
        Double amount = data.getDouble("amount");
        Boolean isPaid = data.getBoolean("is_paid");
        String phone = data.getString("phone");
        java.sql.Date sqlBirthDate = data.getDate("birth_date");
        LocalDate birthdate = null;
        if (sqlBirthDate != null) {
          birthdate = sqlBirthDate.toLocalDate();
        }
        String email = data.getString("email");
        String charityCode = data.getString("charity_code");
  
        Booking booking = new Booking(bookingId, lastName, firstName, amount, isPaid, phone, birthdate, email, date, charityCode, session.getId());
        bookings.add(booking);
      }
    } catch (SQLException e) {
      throw new GetBookingsException(e.getMessage());
    }
    
    return bookings;
  }
  
  public void getAmountsPerActivity(Charity charity) throws GetAmountsPerActivityException {
    String sql = "SELECT SUM(b.amount) as \"somme\", COUNT(*) as \"nombre\", a.title " +
            "FROM booking b " +
            "JOIN session s ON b.session_id = s.session_id " +
            "JOIN activity a ON s.activity_code = a.activity_code " +
            "WHERE b.charity_code = ? " +
            "GROUP BY a.title;";
    
    try {
      PreparedStatement req = connection.prepareStatement(sql);
      
      req.setString(1, charity.getCode());
      
      ResultSet data = req.executeQuery();
    } catch (SQLException e) {
      throw new GetAmountsPerActivityException(e.getMessage());
    }
  }
  
  @Override
  public void getPeoplePerActivityAndCharity(Activity activity, Charity charity) throws GetPeoplePerActivityAndCharityException {
  
  }
}
