package model;

import java.time.LocalTime;

public class Session {
  private Integer id;
  private Integer numDay;
  private LocalTime startHour;
  private LocalTime endHour;
  private Boolean isWeekly;
  private Integer nbMax;
  private Activity activity;
  
  public Session(Integer id) {
    this.id = id;
  }
  
  public Session(Integer id, Integer numDay, LocalTime startHour, LocalTime endHour, Boolean isWeekly, Integer nbMax, Activity activity) {
    this.id = id;
    this.numDay = numDay;
    this.startHour = startHour;
    this.endHour = endHour;
    this.isWeekly = isWeekly;
    this.nbMax = nbMax;
    this.activity = activity;
  }
  
  public Integer getId() {
    return id;
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
