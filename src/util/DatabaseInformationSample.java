package util;

public class DatabaseInformationSample {
  private static String driver = "mariadb";
  private static String host = "localhost";
  private static int port = 3306;
  private static String user = "root";
  private static String password = "root";
  private static String dbName = "booking";
  private static Boolean useSSl = false;
  
  public static String getUrl() {
    return "jdbc:" + driver + "://address=(protocol=tcp)(host=" + host + ")(port=" + port +")/" + dbName + "?useSSl=" + useSSl.toString() ;
  }
  
  public static String getUser() {
    return user;
  }
  
  public static String getPassword() {
    return password;
  }
}