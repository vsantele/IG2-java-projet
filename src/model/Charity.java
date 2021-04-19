package model;

public class Charity {
  private String code;
  private String name;
  private String contact;
  private String address;
  private String city;
  private String zipCode;
  private String country;
  
  public Charity(String code, String name, String contact, String address, String city, String zipCode, String country) {
    this.code = code;
    this.name = name;
    this.contact = contact;
    this.address = address;
    this.city = city;
    this.zipCode = zipCode;
    this.country = country;
  }
  
  @Override
  public String toString() {
    return "Charity{" +
            "code='" + code + '\'' +
            ", name='" + name + '\'' +
            ", contact='" + contact + '\'' +
            ", address='" + address + '\'' +
            ", city='" + city + '\'' +
            ", zipCode='" + zipCode + '\'' +
            ", country='" + country + '\'' +
            '}';
  }
}
