package com.ashwani.shopping.service;

import java.util.Optional;

import com.ashwani.shopping.model.User;

public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User saveUser(User user);

}
