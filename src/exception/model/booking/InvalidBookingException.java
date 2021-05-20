package exception.model.booking;


public class InvalidBookingException extends Exception{
  private String field;
  
  public InvalidBookingException(String field) {
    this.field = field;
  }
  
  public String getMessage() {
    return "Le champ " + field + " est invalide";
  }
}