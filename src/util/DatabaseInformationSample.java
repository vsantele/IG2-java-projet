package util;

public class DatabaseInformationSample {
  static String host = "localhost";
  static int port = 3306;
  static String user = "root";
  static String password = "root";
  static String dbName = "booking";
  static Boolean useSSl = false;
  
  public static String getUrl() {
    return "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSl=" + useSSl.toString();
  }
  
  public static String getUser() {
    return user;
  }
  
  public static String getPassword() {
    return password;
  }
}