package com.smartbot.smartbot.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Where;
import com.smartbot.smartbot.enums.Title;

@Entity
@Table(name = "users")
@Where(clause = "deleted='false'")
public class User extends BaseEntity {

  private static final long serialVersionUID = 1L;
  //@Id
  //@GeneratedValue(generator = "UUID")
  //@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  //private String uuid;
  @Transient
  private String name;
  @Enumerated(EnumType.STRING)
  private Title title;
  //@Column(name = "first_name")
  private String firstName;
  //@Column(name = "last_name")
  private String lastName;
  private String gender;
  private String emailId;
  private String password;
  //@Column(name = "image_url")
  private String imageUrl;
  @Column(nullable = false, columnDefinition = "boolean default false")
  private String deleted;

  @OneToMany(mappedBy = "user")
  private List<Order> userOrders;

  public User() {

  }

  public User(String firstName, String lastName) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Title getTitle() {
    return this.title;
  }

  public void setTitle(Title title) {
    this.title = title;
  }

  public String getName() {
    return this.firstName + " " + this.lastName;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getImageUrl() {
    return this.name;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public String toString() {
    return ("User [, title=" + title + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
        + ", emailId=" + emailId + ",password=" + password + "]");
  }
}