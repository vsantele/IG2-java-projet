package model;

import java.time.LocalDate;

public class Booking {
  private Integer id;
  private String lastname;
  private String firstname;
  private Double amount;
  private Boolean isPaid;
  private String phone;
  private LocalDate birthdate;
  private String email;
  private LocalDate date;
  private Charity charity;
  private Session session;
  
  public Booking(Integer id, String lastname, String firstname, Double amount, Boolean isPaid, String phone, LocalDate birthdate, String email, LocalDate date, Charity charity, Session session) {
    this.id = id;
    this.lastname = lastname;
    this.firstname = firstname;
    this.amount = amount;
    this.isPaid = isPaid;
    this.phone = phone;
    this.birthdate = birthdate;
    this.email = email;
    this.date = date;
    this.charity = charity;
    this.session = session;
  }
  
  @Override
  public String toString() {
    return "Booking{" +
            "id=" + id +
            ", lastname='" + lastname + '\'' +
            ", firstname='" + firstname + '\'' +
            ", amount=" + amount +
            ", isPaid=" + isPaid +
            ", phone='" + phone + '\'' +
            ", birthdate=" + birthdate +
            ", email='" + email + '\'' +
            ", date=" + date +
            ", charity=" + charity +
            ", session=" + session +
            '}';
  }
}
