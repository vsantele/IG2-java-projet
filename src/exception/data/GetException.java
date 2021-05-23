package exception.data;

public class GetException extends Exception{
  private String details;
  
  public GetException(String details) {
    this.details = details;
  }
  public GetException() {
    this(null);
  }
  
  public String getMessage() {
    return "Erreur lors de la récupération des données";
  }
  
  public String getDetails() {
    return details;
  }
}
