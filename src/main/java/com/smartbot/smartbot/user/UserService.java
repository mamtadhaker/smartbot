package com.smartbot.smartbot.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> findAll() {
    List<User> users = new ArrayList<User>();
    userRepository.findAll().forEach(users::add);
    return users;
  }

  public User getUser(UUID id) {
    return userRepository.findOne(id);
  }

  public void addUser(User user) {
    userRepository.save(user);
  }

  public void updateUser(String uuid, User user) {
    userRepository.save(user);
  }

  public void deleteUser(UUID id) {
    userRepository.delete(id);
  }
}