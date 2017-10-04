package com.smartbot.smartbot.user;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.smartbot.smartbot.enums.Title;
import com.smartbot.smartbot.order.Order;;

@Entity
@Table(name = "users")
@Where(clause = "deleted='false'")
public class User {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;
  @Transient
  private String name;
  @Enumerated(EnumType.STRING)
  private Title title;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "image_url")
  private String imageUrl;
  @Column(nullable = false, columnDefinition = "boolean default false")
  private String deleted;

  @OneToMany
  private List<Order> orders;

  public UUID getId() {
    return this.id;
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

  public String getImageUrl() {
    return this.name;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public String toString() {
    return "User [title = " + this.getTitle().toString() + ", first_name = " + this.getFirstName() + ", last_name = "
        + this.getLastName();
  }
}