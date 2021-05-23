package exception.data;

public class DeleteBookingException extends DataException {
  
  public DeleteBookingException() {
    this(null);
  }
  
  public DeleteBookingException(String details) {
    super("Suppression", details);
  }
  
}
