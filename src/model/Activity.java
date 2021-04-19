package model;

public class Activity {
  private String code;
  private String label;
  
  public Activity(String code, String label) {
    this.code = code;
    this.label = label;
  }
  
  @Override
  public String toString() {
    return "Activity{" +
            "code='" + code + '\'' +
            ", label='" + label + '\'' +
            '}';
  }
}
