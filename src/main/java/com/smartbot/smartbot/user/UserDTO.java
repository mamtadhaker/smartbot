package com.smartbot.smartbot.user;

import com.smartbot.smartbot.entity.User;
import com.smartbot.smartbot.enums.Title;

public class UserDTO {

  private Title title;
  private String firstName;
  private String lastName;
  private String imageUrl;
  private String uuid;
  private boolean isNewUser;

  public UserDTO(User user) {
    super();
    this.title = user.getTitle();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.imageUrl = user.getImageUrl();
    this.uuid = user.getId();
  }

  public UserDTO() {

  }

  public Title getTitle() {
    return title;
  }

  public void setTitle(Title title) {
    this.title = title;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public boolean getIsNewUser() {
    return isNewUser;
  }

  public void setIsNewUser(boolean isNewUser) {
    this.isNewUser = isNewUser;
  }
}