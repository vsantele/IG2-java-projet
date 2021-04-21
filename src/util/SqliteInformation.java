package util;

public class SqliteInformation {
  static private String path = "./db/booking.db";

  public static String getUrl() {
    return "jdbc:sqlite:" +path ;
  }
}
