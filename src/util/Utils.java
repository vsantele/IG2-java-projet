package util;

import java.time.*;

public class Utils {
  static public java.sql.Date toSqlDate(LocalDate date) {
    return new java.sql.Date(date.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC));
  }
  
  static public java.sql.Time toSqlTime(LocalTime time) {
    return new java.sql.Time(time.toEpochSecond(LocalDate.EPOCH,ZoneOffset.UTC )*1000);
  }
}
