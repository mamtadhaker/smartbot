package com.smartbot.smartbot.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

  @Id
 // @GeneratedValue(generator = "uuid4")
 @GeneratedValue(generator = "uuid4")
  private String uuid;
  private String name;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

}