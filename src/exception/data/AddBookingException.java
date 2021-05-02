package exception.data;

public class AddBookingException extends Exception {
  private String msg;
  
  public AddBookingException(String msg) {
    this.msg = msg;
  }
  
  public String getMessage() {
    return "Le requête a retourné le message: " + msg;
  }
}
