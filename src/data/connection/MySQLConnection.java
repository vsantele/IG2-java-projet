package data.connection;

import util.DatabaseInformation;

import java.sql.*;

public class MySQLConnection implements DBConnection {
  static private Connection dbConnection;

  private MySQLConnection() {}
  
  static public Connection getInstance() {
    if (dbConnection == null) {
      try {
        dbConnection = DriverManager.getConnection(DatabaseInformation.getUrl(), DatabaseInformation.getUser(), DatabaseInformation.getPassword());
        
      } catch (SQLException exception) {
        exception.printStackTrace();
      }
    }
    return dbConnection;
  }

}
