package util;

import java.time.*;

public class Utils {
  static public java.sql.Date toSqlDate(LocalDate date) {
    return new java.sql.Date(date.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC));
  }
}
