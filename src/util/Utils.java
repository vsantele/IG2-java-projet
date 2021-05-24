package util;

import java.time.*;
import java.util.regex.Pattern;

public class Utils {
  static public java.sql.Date toSqlDate(LocalDate date) {
    return java.sql.Date.valueOf(date);
  }
  
  static public java.sql.Time toSqlTime(LocalTime time) {
    return java.sql.Time.valueOf(time);
  }
  
  public static Boolean isPhoneValid(String phone) {
    Pattern pattern = Pattern.compile("(((\\+[0-9]{2,3})|(00[0-9]{2,3}))\\s)?((((\\(0\\))|0)?[0-9]{1,3}/)|([0-9]{1,2}\\.[0-9]{2}\\.))?[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}(\\.[0-9]{2})?");
    return pattern.matcher(phone).matches();
  }
  
  public static Boolean isEmailValid(String email) {
    Pattern pattern = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
    return pattern.matcher(email).matches();
  }
}
