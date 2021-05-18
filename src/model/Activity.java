package model;

import java.util.Objects;

public class Activity {
  private String code;
  private String label;
  
  public Activity(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public String getCode() {
    return code;
  }
  
  public String getLabel() {
    return label;
  }
  
  @Override
  public String toString() {
    return "Activity{" +
            "code='" + code + '\'' +
            ", label='" + label + '\'' +
            '}';
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Activity activity = (Activity) o;
    return Objects.equals(code, activity.code);
  }
  
}
