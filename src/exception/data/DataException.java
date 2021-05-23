package exception.data;

public abstract class DataException extends Exception{
  private final String operation;
  private final String details;
  
  public DataException(String operation, String details) {
    this.operation = operation;
    this.details = details;
  }
  
  public  DataException(String operation) {
    this(operation, null);
  }
  
  public String getMessage() {
    return "Une erreur est survenue lors de l'opération suivante: " + operation;
  }
  
  public String getDetails() {
    return details;
  }
}
