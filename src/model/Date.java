package model;

import java.time.LocalDate;

public class Date {
  public static final String CUSTOM = "custom";
  public static final String CANCELED = "canceled";

  private final Integer id;
  private final String type;
  private final LocalDate date;
  
  public Date(Integer id, String type, LocalDate date) {
    this.id = id;
    this.type = type;
    this.date = date;
  }
  
  public Date(String type, LocalDate date) {
    this(null, type, date);
  }
  
  public LocalDate getDate() {
    return date;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Date{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", date=" + date +
            '}';
  }
}
