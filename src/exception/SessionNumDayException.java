package exception;

public class SessionNumDayException extends Exception{
  private final Integer numDay;
  
  public SessionNumDayException(Integer numDay) {
    this.numDay = numDay;
  }
  
  @Override
  public String getMessage() {
    return "Le nombre doit-être compris en 1 et 7! reçu: " + numDay;
  }
}
