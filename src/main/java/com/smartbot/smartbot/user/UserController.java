package com.smartbot.smartbot.user;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  // @RequestMapping("/users")
  // public List<User> index() {
  //   return userService.findAll();
  // }

  // @RequestMapping(method = RequestMethod.POST, value = "/users")
  // public void createUser(@RequestBody User user) {
  //   userService.addUser(user);
  // }

  // @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
  // public void updateUser(@RequestBody User user) {
  //   userService.addUser(user);
  // }

  // @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
  // public void deleteUser(@PathVariable UUID id) {
  //   userService.deleteUser(id);
  // }

  @RequestMapping(value = "/user/store", method = RequestMethod.POST)
  public boolean storeUser(@ModelAttribute UserStoreDTO userStoreDTO) {
    LOGGER.debug("Request received to store user");

    userService.saveUser(userStoreDTO);
    return true;
  }
}
