package data.access;

import model.*;

import java.sql.SQLException;

public interface BookingDataAccess {
  int addCharity(Charity charity) throws SQLException;
}
