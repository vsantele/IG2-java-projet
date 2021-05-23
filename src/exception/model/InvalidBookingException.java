package exception.model;


public class InvalidBookingException extends Exception{
  private final String field;
  
  public InvalidBookingException(String field) {
    this.field = field;
  }
  
  public String getMessage() {
    return "Le champ " + field + " est invalide";
  }
}
