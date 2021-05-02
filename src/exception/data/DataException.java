package exception.data;

public abstract class DataException extends Exception{
  private String msg;
  
  public DataException(String msg) {
    this.msg = msg;
  }
  
  public String getMessage() {
    return "Le requête a retourné le message: " + msg;
  }
}
