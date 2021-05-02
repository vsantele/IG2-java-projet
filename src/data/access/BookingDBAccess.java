package data.access;

import data.connection.MariadbConnection;
import exception.data.*;
import model.*;
import model.Date;
import util.Utils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookingDBAccess implements BookingDataAccess {
  public BookingDBAccess() {}
  
  public int addBooking(Booking booking) throws AddBookingException {
    Connection connection = MariadbConnection.getInstance();
    String sql = "INSERT INTO booking(last_name, first_name, amount, is_paid, phone, birth_date, email, date, charity_code, session_id) VALUES (?,?,?,?,?,?,?,?,?,?);";
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
      
      req.setString(9, booking.getCharity().getCode());
      req.setInt(10, booking.getSession().getId());
      
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
    Connection connection = MariadbConnection.getInstance();
    String sql = "UPDATE booking set last_name = ?, first_name = ?, amount = ?, is_paid = ?, phone = ?, birth_date = ?, email = ?, date = ?, charity_code = ?, session_id = ? WHERE booking_id = ?;";
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
    Connection connection = MariadbConnection.getInstance();
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
  
  public ArrayList<Date> getDates(Session session) {
    Connection connection = MariadbConnection.getInstance();
    ArrayList<Date> dates = new ArrayList<>();
    String sql = "SELECT date_id, date, type FROM date WHERE session_id = ?";
    
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
      e.printStackTrace();
    }
    
    return dates;
  }
  
}
