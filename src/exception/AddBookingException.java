package exception;

public class AddBookingException extends Exception {
  private int code;
  
  public AddBookingException(int code) {
    this.code = code;
  }
  
  public String getMessage() {
    return "Le requête a retourné le code: " + code;
  }
}
