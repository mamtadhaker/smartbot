package com.smartbot.smartbot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("/user")
  public String sayHi() {
    return "Hi";
  }

  @RequestMapping(method = RequestMethod.POST, value = "/users")
  public boolean createUser(){

  }
}