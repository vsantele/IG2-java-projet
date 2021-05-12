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
  private String charityCode;
  private Session session;
  private Integer sessionId;

  public Booking(Integer id, String lastname, String firstname, Double amount, Boolean isPaid, String phone, LocalDate birthdate, String email, LocalDate date, Charity charity, Session session) {
    setId(id);
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
  
  public Booking(Integer id, String lastname, String firstname, Double amount, Boolean isPaid, String phone, LocalDate birthdate, String email, LocalDate date, String charityCode, Integer sessionId) {
    this(id, lastname, firstname, amount, isPaid, phone, birthdate, email, date, (Charity) null,  (Session) null);
    this.charityCode = charityCode;
    this.sessionId = sessionId;
  }

  public Booking(String lastname, String firstname, Double amount, Boolean isPaid, String phone, LocalDate birthdate, String email, LocalDate date, Charity charity, Session session) {
    this(null, lastname, firstname, amount, isPaid, phone, birthdate, email, date, charity, session);
  }
  
  public Booking(String lastname, String firstname, Double amount, LocalDate birthdate) {
    this(null, lastname, firstname, amount, null, null, birthdate, (String) null, null, (String) null, null);
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public Integer getId() {
    return id;
  }
  
  public String getLastname() {
    return lastname;
  }
  
  public String getFirstname() {
    return firstname;
  }
  
  public Double getAmount() {
    return amount;
  }
  
  public Boolean getPaid() {
    return isPaid;
  }
  
  public String getPhone() {
    return phone;
  }
  
  public LocalDate getBirthdate() {
    return birthdate;
  }
  
  public String getEmail() {
    return email;
  }
  
  public LocalDate getDate() {
    return date;
  }
  
  public Charity getCharity() {
    return charity;
  }
  
  public String getCharityCode() {
    if (charity == null) {
      return charityCode;
    } else {
      return charity.getCode();
    }
  }
  
  public Session getSession() {
    return session;
  }
  
  public Integer getSessionId() {
    if (session == null) {
      return sessionId;
    } else {
      return session.getId();
    }
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
