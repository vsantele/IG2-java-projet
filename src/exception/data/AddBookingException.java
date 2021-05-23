package exception.data;

public class AddBookingException extends DataException {
  
  public AddBookingException() {
    super(null);
  }
  
  public AddBookingException(String details) {
    super("Ajout", details);
  }
}
