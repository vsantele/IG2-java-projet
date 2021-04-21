package data.access;

import data.connection.SqliteConnection;
import model.*;

import java.sql.*;

public class BookingDBAccess implements BookingDataAccess {
  final private Connection dbConnection;

  public BookingDBAccess() {
    dbConnection = SqliteConnection.getInstance();
  }

  public int addCharity(Charity charity) throws SQLException {
    String sql = "INSERT INTO charity(charity_code, name, contact, address, city, zip_code, country) VALUES (?,?,?,?,?,?,?)";
    PreparedStatement req = dbConnection.prepareStatement(sql);
    req.setString(1, charity.getCode());
    req.setString(2, charity.getName());
    req.setString(3, charity.getContact());
    if (charity.getAddress() == null) {
      req.setNull(4, Types.VARCHAR);
    } else {
      req.setString(4, charity.getAddress());
    }
    if (charity.getCity() == null) {
      req.setNull(5, Types.VARCHAR);
    } else {
      req.setString(5, charity.getCity());
    }

    if (charity.getZipCode() == null) {
      req.setNull(6, Types.VARCHAR);
    } else {
      req.setString(6, charity.getZipCode());
    }

    if (charity.getCountry() == null) {
      req.setNull(7, Types.VARCHAR);
    } else {
      req.setString(7, charity.getCountry());
    }
    return req.executeUpdate();
  }
}
