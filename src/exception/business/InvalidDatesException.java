package exception.business;

import java.time.LocalDate;

public class InvalidDatesException extends Exception {
  private final LocalDate start;
  private final LocalDate end;
  
  public InvalidDatesException(LocalDate start, LocalDate end) {
    this.start = start;
    this.end = end;
  }
  
  public String getMessage() {
    return start + "  est après " + end;
  }
}
