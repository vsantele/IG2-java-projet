package exception.model;


public class InvalidBookingException extends Exception{
  private final String field;
  private final String details;
  
  public InvalidBookingException(String field) {
    this(field, null);
  }
  public InvalidBookingException(String field, String details) {
    this.field = field;
    this.details = details;
  }
  
  public String getMessage() {
    return "Le champ " + field + " est invalide";
  }
  public String getDetails() {
    return details;
  }
}
