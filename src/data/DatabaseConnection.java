package data;

import util.DatabaseInformation;

import java.sql.*;

public class DatabaseConnection {
  static private Connection dbConnection;
  
  private DatabaseConnection() {
  
  }
  
  static public Connection getInstance() {
    if (dbConnection == null) {
      try {
        dbConnection = DriverManager.getConnection(DatabaseInformation.getUrl(), DatabaseInformation.getUser(), DatabaseInformation.getPassword());
        
      } catch (SQLException exception) {
        System.err.println(exception.getMessage());
        
      }
    }
    return dbConnection;
  }
}
