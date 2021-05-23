package exception.data;

public class DeleteBookingException extends DataException {
  private String details;
  
  public DeleteBookingException() {
    this(null);
  }
  
  public DeleteBookingException(String details) {
    super("Suppression", details);
  }
  
}
