package model;

import java.util.Objects;

public class Activity {
  private final String code;
  private final String title;
  
  public Activity(String code, String label) {
    this.code = code;
    this.title = label;
  }

  public String getCode() {
    return code;
  }
  
  public String getTitle() {
    return title;
  }
  
  @Override
  public String toString() {
    return "Activity{" +
            "code='" + code + '\'' +
            ", label='" + title + '\'' +
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
