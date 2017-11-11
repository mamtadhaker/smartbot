package com.smartbot.smartbot.user;

import org.springframework.data.repository.CrudRepository;

import com.smartbot.smartbot.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

  public User findByUuid(String uuid);
}