package com.smartbot.smartbot.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "orders")
@Where(clause = "deleted='false'")
public class Order {

  
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  // public UUID getId() {
  //   return this.id;
  // }

  // public User getUser() {
  //   return this.user;
  // }

  // public void setUser(User user) {
  //   this.user = user;
  // }
}