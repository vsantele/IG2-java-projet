package data.connection;

import util.SqliteInformation;

import java.sql.*;

public class SqliteConnection implements DBConnection {
  static private Connection sqliteConnection;

  private SqliteConnection() {}

  static public Connection getInstance() {
    if (sqliteConnection == null) {
      try {
        sqliteConnection = DriverManager.getConnection(SqliteInformation.getUrl());
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
    return sqliteConnection;
  }
}
