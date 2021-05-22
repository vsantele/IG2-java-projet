package exception.data;

public class GetException extends Exception{
  private String msg;
  
  public GetException(String msg) {
    this.msg = msg;
  }
  
  public String getMessage() {
    return "Erreur lors de la récupération des données...";
  }
}
