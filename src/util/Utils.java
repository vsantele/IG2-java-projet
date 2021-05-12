package util;

import java.time.*;

public class Utils {
  static public java.sql.Date toSqlDate(LocalDate date) {
    return java.sql.Date.valueOf(date);
  }
  
  static public java.sql.Time toSqlTime(LocalTime time) {
    return java.sql.Time.valueOf(time);
  }
}
