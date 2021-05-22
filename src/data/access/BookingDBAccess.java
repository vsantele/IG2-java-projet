package data.access;

import data.connection.MariadbConnection;
import exception.SessionNumDayException;
import exception.data.*;
import exception.model.booking.InvalidBookingException;
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
    String sql = "INSERT INTO booking(lastname, firstname, amount, is_paid, phone, birthdate, email, date, charity_code, session_id) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?);";
    try {
      PreparedStatement req = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

      req.setString(1, booking.getLastname());
      req.setString(2, booking.getFirstname());
      req.setDouble(3, booking.getAmount());
      req.setBoolean(4, booking.isPaid());
      req.setString(5, booking.getPhone());
      
      if (booking.getBirthdate() == null) {
        req.setNull(6, Types.DATE);
      } else {
        java.sql.Date sqlBirthDate = Utils.toSqlDate(booking.getBirthdate());
        req.setDate(6, sqlBirthDate);
      }

      if (booking.getEmail() == null) {
        req.setNull(7, Types.VARCHAR);
      } else {
        req.setString(7, booking.getEmail());
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
    String sql = "UPDATE booking set lastname = ?, firstname = ?, amount = ?, is_paid = ?, phone = ?, birthdate = ?, email = ?, date = ?, charity_code = ?, session_id = ? " +
            "WHERE booking_id = ?;";
    try {
      PreparedStatement req = connection.prepareStatement(sql);
  
      req.setString(1, booking.getLastname());
      req.setString(2, booking.getFirstname());
      req.setDouble(3, booking.getAmount());
      req.setBoolean(4, booking.isPaid());
      req.setString(5, booking.getPhone());
  
      if (booking.getBirthdate() == null) {
        req.setNull(6, Types.DATE);
      } else {
        java.sql.Date sqlBirthDate = Utils.toSqlDate(booking.getBirthdate());
        req.setDate(6, sqlBirthDate);
      }
  
      if (booking.getEmail() == null) {
        req.setNull(7, Types.VARCHAR);
      } else {
        req.setString(7, booking.getEmail());
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
  
  public ArrayList<Date> getDates(Session session, LocalDate start, LocalDate end) throws GetException {
    ArrayList<Date> dates = new ArrayList<>();
    String sql = "SELECT date_id, date, type " +
            "FROM date " +
            "WHERE session_id = ? " +
            "AND date BETWEEN ? AND ?";
    
    try {
      PreparedStatement req = connection.prepareStatement(sql);
      
      req.setInt(1, session.getId());
      req.setDate(2, Utils.toSqlDate(start));
      req.setDate(3, Utils.toSqlDate(end));
      
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
      throw new GetException(e.getMessage());
    }
    
    return dates;
  }
  
  public Activity getActivity(Integer session) throws GetException {
    String sql = "SELECT a.activity_code, a.title FROM activity a JOIN session s ON a.activity_code = s.activity_code WHERE s.session_id = ?;";
    
    Activity activity = null;
    try {
      PreparedStatement req = connection.prepareStatement(sql);
      
      req.setInt(1, session);
      
      ResultSet data = req.executeQuery();
      
      while (data.next()) {
        activity = new Activity(data.getString("activity_code"), data.getString("title"));
      }
    } catch (SQLException e) {
      throw new GetException(e.getMessage());
    }
    return activity;
  }
  
  public ArrayList<Activity> getActivities() throws GetException {
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
      throw new GetException(e.getMessage());
    }
    
    return activities;
  }
  
  public ArrayList<Session> getSessions(Activity activity) throws GetException {
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
      throw new GetException(e.getMessage());
    }
    
    return sessions;
  }
  
  public Charity getCharity(String charityCode) throws GetException {
    String sql = "SELECT name, contact, address, city, country, zip_code FROM charity WHERE charity_code = ?;";
  
    Charity charity = null;
    try {
      PreparedStatement req = connection.prepareStatement(sql);
    
      req.setString(1, charityCode);
    
      ResultSet data = req.executeQuery();
    
      while (data.next()) {
        String name = data.getString("name");
        String contact = data.getString("contact");
        String address = data.getString("address");
        String city = data.getString("city");
        String country = data.getString("country");
        String zipCode = data.getString("zip_code");
        charity = new Charity(charityCode, name, contact, address, city, country, zipCode);
      }
    } catch (SQLException e) {
      throw new GetException(e.getMessage());
    }
    return charity;
  }
  
  public ArrayList<Charity> getCharities() throws GetException{
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
      throw new GetException(e.getMessage());
    }
    
    return charities;
  }
  
  public ArrayList<Booking> getBookings() throws GetException {
    ArrayList<Booking> bookings = new ArrayList<>();
    String sql = "SELECT b.booking_id, b.lastname, b.firstname, b.amount, b.is_paid, b.phone, b.birthdate, b.email, b.date, c.charity_code, c.name, s.session_id, s.num_day, s.start_hour, s.end_hour, s.is_weekly, s.nb_max, a.activity_code, a.title FROM booking b " +
            "JOIN charity c ON b.charity_code = c.charity_code " +
            "JOIN session s ON b.session_id = s.session_id " +
            "JOIN activity a on s.activity_code = a.activity_code " +
            "ORDER BY b.date DESC, a.title, s.num_day, s.start_hour, b.lastname, b.firstname;";
  
    try {
      PreparedStatement req = connection.prepareStatement(sql);
    
      ResultSet data = req.executeQuery();
      
      while(data.next()) {
        Integer bookingId = data.getInt("b.booking_id");
        String lastName = data.getString("b.lastname");
        String firstName = data.getString("b.firstname");
        Double amount = data.getDouble("b.amount");
        Boolean isPaid = data.getBoolean("b.is_paid");
        String phone = data.getString("b.phone");
        java.sql.Date sqlBirthDate = data.getDate("b.birthdate");
        LocalDate birthdate = null;
        if (sqlBirthDate != null) {
          birthdate = sqlBirthDate.toLocalDate();
        }
        String email = data.getString("b.email");
        LocalDate date = data.getDate("b.date").toLocalDate();
        
        String charityCode = data.getString("c.charity_code");
        String charityName = data.getString("c.name");
        Charity charity = new Charity(charityCode, charityName);
  
        String activityCode = data.getString("a.activity_code");
        String activityTitle = data.getString("a.title");
        Activity activity = new Activity(activityCode, activityTitle);
        
        Integer sessionId = data.getInt("s.session_id");
        Integer numDay = data.getInt("s.num_day");
        LocalTime startHour = data.getTime("s.start_hour").toLocalTime();
        LocalTime endHour = data.getTime("s.end_hour").toLocalTime();
        Boolean isWeekly = data.getBoolean("s.is_weekly");
        Integer nbMax = data.getInt("s.nb_max");
        
        Session session = new Session(sessionId, numDay, startHour, endHour, isWeekly, nbMax, activity);
        
  
        Booking booking = new Booking(bookingId, firstName, lastName, amount, isPaid, phone, birthdate, email, date, charity, session );
        bookings.add(booking);
      }
    } catch (SQLException | InvalidBookingException | SessionNumDayException e) {
      throw new GetException(e.getMessage());
    }
    
    return bookings;
  }
  
  public ArrayList<Booking> getBookings(Session session, LocalDate date) throws GetException {
    ArrayList<Booking> bookings = new ArrayList<>();
    String sql = "SELECT b.booking_id, b.lastname, b.firstname, b.amount, b.is_paid, b.phone, b.birthdate, b.email, c.charity_code , c.name " +
            "FROM booking b " +
            "JOIN charity c ON b.charity_code = c.charity_code " +
            "WHERE session_id = ? AND date = ?;";
  
    try {
      PreparedStatement req = connection.prepareStatement(sql);
      
      req.setInt(1, session.getId());
      req.setDate(2, Utils.toSqlDate(date));
      
      ResultSet data = req.executeQuery();
      
      while (data.next()) {
        Integer bookingId = data.getInt("b.booking_id");
        String lastName = data.getString("b.lastname");
        String firstName = data.getString("b.firstname");
        Double amount = data.getDouble("b.amount");
        Boolean isPaid = data.getBoolean("b.is_paid");
        String phone = data.getString("b.phone");
        java.sql.Date sqlBirthDate = data.getDate("b.birthdate");
        LocalDate birthdate = null;
        if (sqlBirthDate != null) {
          birthdate = sqlBirthDate.toLocalDate();
        }
        String email = data.getString("b.email");
        
        String charityCode = data.getString("c.charity_code");
        String charityName = data.getString("c.name");
        
        Charity charity = new Charity(charityCode, charityName);
        
        
  
        Booking booking = new Booking(bookingId, lastName, firstName, amount, isPaid, phone, birthdate, email, date, charity, session);
        bookings.add(booking);
      }
    } catch (SQLException | InvalidBookingException e) {
      throw new GetException(e.getMessage());
    }
    
    return bookings;
  }
  
  public Boolean isSessionFull(Session session, LocalDate date) throws GetException {
    String sql = "SELECT count(*) as count, s.nb_max FROM booking b JOIN session s ON b.session_id = s.session_id  WHERE b.session_id = ? AND b.date = ?";
    
    Boolean isFull = null;
  
    try {
      PreparedStatement req = connection.prepareStatement(sql);
      
      req.setInt(1, session.getId());
      req.setDate(2,Utils.toSqlDate(date));
      
      ResultSet data = req.executeQuery();
      
      while (data.next()) {
        Integer count = data.getInt("count");
        Integer nbMax = data.getInt("nb_max");
        
        isFull = count >= nbMax;
      }
    } catch (SQLException e) {
      throw new GetException(e.getMessage());
    }
    return isFull;
  }
  
  @Override
  public Double getTotal() throws GetException {
    String sql = "SELECT SUM(amount) as amounts FROM booking ;" +
            "";
    Double result = null;
    try {
      PreparedStatement req = connection.prepareStatement(sql);
    
      ResultSet data = req.executeQuery();
    
      while (data.next()) {
        result = data.getDouble("amounts");
      }
    } catch (SQLException e) {
      throw new GetException(e.getMessage());
    }
    return result;
  }
  
  public ArrayList<AmountActivity> getAmountsPerActivity(Charity charity) throws GetException {
    String sql = "SELECT SUM(b.amount) as amounts, a.title " +
            "FROM booking b " +
            "JOIN session s ON b.session_id = s.session_id " +
            "JOIN activity a ON s.activity_code = a.activity_code " +
            "WHERE b.charity_code = ? " +
            "GROUP BY a.title;";
    ArrayList<AmountActivity> resultList = new ArrayList<>();
    try {
      PreparedStatement req = connection.prepareStatement(sql);
      
      req.setString(1, charity.getCode());
      
      ResultSet data = req.executeQuery();
      
      while (data.next()) {
        Double amount = data.getDouble("amounts");
        String activity = data.getString("title");
        AmountActivity amountActivity = new AmountActivity(amount, activity);
        resultList.add(amountActivity);
      }
    } catch (SQLException e) {
      throw new GetException(e.getMessage());
    }
    return resultList;
  }
  
  @Override
  public ArrayList<Booking> getPeoplePerActivityAndCharity(Activity activity, Charity charity) throws GetException {
    String sql = "SELECT b.booking_id, b.firstname, b.lastname, b.amount, b.is_paid, b.phone,  b.birthdate, b.email, b.date, s.session_id " +
            "FROM booking b " +
            "JOIN session s ON b.session_id = s.session_id " +
            "JOIN charity c ON b.charity_code = c.charity_code " +
            "WHERE s.activity_code = ? AND c.charity_code = ?";
    
    ArrayList<Booking> resultList = new ArrayList<>();
    try {
      PreparedStatement req = connection.prepareStatement(sql);
      
      req.setString(1,activity.getCode());
      req.setString(2,charity.getCode());
      
      ResultSet data = req.executeQuery();
        while (data.next()){
          Integer id = data.getInt("booking_id");
          String firstname = data.getString("firstname");
          String lastname = data.getString("lastname");
          Double amount = data.getDouble("amount");
          java.sql.Date sqlBirthDate = data.getDate("birthdate");
          LocalDate birthdate = null;
          if (sqlBirthDate != null) {
            birthdate = sqlBirthDate.toLocalDate();
          }
          Boolean isPaid = data.getBoolean("is_paid");
          String phone = data.getString("phone");
          String email = data.getString("email");
          java.sql.Date sqlDate = data.getDate("date");
          LocalDate date = null;
          if (sqlDate != null) {
            date = sqlDate.toLocalDate();
          }
          Integer sessionId = data.getInt("session_id");
          Booking booking = new Booking(id, lastname, firstname, amount, isPaid, phone, birthdate, email, date,  charity.getCode(), sessionId);
          resultList.add(booking);
        }
    } catch (SQLException | InvalidBookingException e ) {
      throw new GetException(e.getMessage());
    }
    return resultList;
  }
  
  public ArrayList<Charity> getCharityAtHour(LocalTime time) throws GetException {
    String sql = "SELECT c.charity_code, c.name, c.contact, c.address, c.city, c.zip_code, c.country FROM charity c " +
            "JOIN booking b ON c.charity_code = b.charity_code " +
            "JOIN session s ON b.session_id = s.session_id " +
            "WHERE ? BETWEEN s.start_hour AND s.end_hour " +
            "GROUP BY c.name;";
    ArrayList<Charity> resultList = new ArrayList<>();
    try {
      PreparedStatement req = connection.prepareStatement(sql);
    
      req.setTime(1,Utils.toSqlTime(time));
      
      ResultSet data = req.executeQuery();
      
      while (data.next()) {
        String charityCode = data.getString("charity_code");
        String name = data.getString("name");
        String contact = data.getString("contact");
        String address = data.getString("address");
        String city = data.getString("city");
        String zipCode = data.getString("zip_code");
        String country = data.getString("country");
        
        resultList.add(new Charity(charityCode, name, contact, address, city, zipCode, country));
      }
      
    } catch (SQLException e) {
      throw new GetException(e.getMessage());
    }
    
    return resultList;
  }
}
