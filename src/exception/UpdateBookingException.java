package exception;

public class UpdateBookingException extends Exception {
  private int code;
  
  public UpdateBookingException(int code) {
    this.code = code;
  }
  
  public String getMessage() {
    return "Le requête a retourné le code: " + code;
  }
}
