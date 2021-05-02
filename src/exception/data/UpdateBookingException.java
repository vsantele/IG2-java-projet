package exception.data;

public class UpdateBookingException extends Exception {
  private String msg;
  
  public UpdateBookingException(String msg) {
    this.msg = msg;
  }
  
  public String getMessage() {
    return "Le requête a retourné le message: " + msg;
  }
}
