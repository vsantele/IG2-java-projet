import data.DatabaseConnection;

import java.sql.*;


public class Main {
  public static void main(String[] args) {
      Connection connection = DatabaseConnection.getInstance();
  }
}
