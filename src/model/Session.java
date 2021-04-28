package model;

import exception.SessionNumDayException;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Session {
  private Integer id;
  private DayOfWeek numDay;
  private LocalTime startHour;
  private LocalTime endHour;
  private Boolean isWeekly;
  private Integer nbMax;
  private Activity activity;
  
  public Session(Integer id) {
    this.id = id;
  }
  
  public Session(Integer id, Integer numDay, LocalTime startHour, LocalTime endHour, Boolean isWeekly, Integer nbMax, Activity activity) throws SessionNumDayException {
    this.id = id;
    setNumDay(numDay);
    this.startHour = startHour;
    this.endHour = endHour;
    this.isWeekly = isWeekly;
    this.nbMax = nbMax;
    this.activity = activity;
  }
  
  public Session(Integer id, Integer numDay, Boolean isWeekly) throws SessionNumDayException {
    this.id = id;
    setNumDay(numDay);
    this.isWeekly = isWeekly;
  }
  
  public void setNumDay(Integer numDay) throws SessionNumDayException {
    if (numDay < 1 || numDay > 7) {
      throw new SessionNumDayException(numDay);
    }
    this.numDay = DayOfWeek.of(numDay);
  }
  
  public Integer getId() {
    return id;
  }
  
  public Boolean getWeekly() {
    return isWeekly;
  }
  
  public DayOfWeek getNumDay() {
    return numDay;
  }
  
  @Override
  public String toString() {
    return "Session{" +
            "id=" + id +
            ", numDay=" + numDay +
            ", startHour=" + startHour +
            ", endHour=" + endHour +
            ", isWeekly=" + isWeekly +
            ", nbMax=" + nbMax +
            ", activity=" + activity +
            '}';
  }
}
