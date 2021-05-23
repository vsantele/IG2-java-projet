package exception.data;

public class UpdateBookingException extends DataException {
  
  public UpdateBookingException() {
    this(null);
  }
  
  public UpdateBookingException(String details) {
    super("Mise à jour", details);
  }
}
