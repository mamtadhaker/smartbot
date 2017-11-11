package com.smartbot.smartbot.user;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartbot.smartbot.entity.User;

@Service
public interface UserService {

  // public List<User> findAll();

  // public User getUser(UUID id);

  // public void addUser(User user);

  // public void updateUser(String uuid, User user);

  // public void deleteUser(UUID id);

  public User saveUser(UserStoreDTO userStoreDTO);

  public UserDTO getUserByImage(MultipartFile image);

  public User getUserByUuid(String uuid);
}