package exception.data;

public class DeleteBookingException extends Exception{
  private String msg;
  
  public DeleteBookingException(String msg) {
    this.msg = msg;
  }
  
  public String getMessage() {
    return "Le requête a retourné le message: " + msg;
  }
}
