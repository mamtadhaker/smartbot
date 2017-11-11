package com.smartbot.smartbot.user;

import org.springframework.web.multipart.MultipartFile;

public class UserStoreDTO {

  private String firstName;
  private String lastName;
  private MultipartFile image;

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

  public MultipartFile getImage() {
    return image;
  }

  public void setImage(MultipartFile image) {
    this.image = image;
  }

  public UserStoreDTO() {
    super();
  }

  public UserStoreDTO(String firstName, String lastName, MultipartFile image) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.image = image;
  }

}