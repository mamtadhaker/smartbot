package com.smartbot.smartbot.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("/users")
  public List<User> index() {
    return userService.findAll();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/users")
  public void createUser(@RequestBody User user) {
    userService.addUser(user);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
  public void updateUser(@RequestBody User user) {
    userService.addUser(user);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
  public void deleteUser(@PathVariable UUID id) {
    userService.deleteUser(id);
  }
}
