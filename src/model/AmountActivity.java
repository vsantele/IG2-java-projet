package model;

public class AmountActivity {
  private Double amount;
  private String activity;
  
  public AmountActivity(Double amount, String activity) {
    this.amount = amount;
    this.activity = activity;
  }
  
  public Double getAmount() {
    return amount;
  }
  
  public String getActivity() {
    return activity;
  }
  
  @Override
  public String toString() {
    return "AmountActivity{" +
            "amount=" + amount +
            ", activity='" + activity + '\'' +
            '}';
  }
}
