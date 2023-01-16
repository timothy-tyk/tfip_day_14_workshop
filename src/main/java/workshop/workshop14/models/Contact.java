package workshop.workshop14.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Contact implements Serializable {
  @NotNull(message = "Name cannot be null")
  @Size (min=3, max=64,message = "Name must be between 3 to 64 characters")
  private String name;

  @Email(message = "Enter a valid email")
  private String email;

  @Size(min = 7, message = "Phone number must be at least 7 numbers")
  private String phoneNumber;

  private String id;

  @Past(message = "Cannot be born in the future")
  @NotNull(message = "Enter a date of birth")
  @DateTimeFormat(pattern = "MM-dd-yyyy")
  private LocalDate dateOfBirth;
  
// Getters and Setters
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPhoneNumber() {
    return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }
  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
  
  // Constructors
  public Contact(){
    this.id = generateId(8);
  }
  public Contact(String id, String name, String email, String phoneNumber){
    this.id = id;
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }
  public Contact(String name, String email, String phoneNumber, LocalDate dateOfBirth){
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.dateOfBirth = dateOfBirth;
    this.id = generateId(8);
  }

  // synchronous method, so that the id will not be the same for different requests at the same time
  // synchronized only takes in 1 request at a time (queue)
  private synchronized String generateId(int numOfChar){
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    while(sb.length()<numOfChar){
      sb.append(Integer.toHexString(random.nextInt()));
    }
    // toHexString makes it long, need to reduce string to 8 characters
    return sb.toString().substring(0,numOfChar);
  }
}


