package util;

public class DatabaseInformation {
  private static final String driver = "mysql";
  private static final String host = "localhost";
  private static final int port = 3306;
  private static final String user = "root";
  private static final String password = "root";
  private static final String dbName = "booking";
  private static final Boolean useSSl = false;
  
  public static String getUrl() {
    return "jdbc:" + driver + "://address=(protocol=tcp)(host=" + host + ")(port=" + port +")/" + dbName + "?useSSl=" + useSSl;
  }
  
  public static String getUser() {
    return user;
  }
  
  public static String getPassword() {
    return password;
  }
}
