package com.jefferpgdev.hulkstore.service;

import com.jefferpgdev.hulkstore.model.User;
import org.springframework.http.HttpStatus;

public interface UserService {

    Iterable<User> getUsers();

    User getUser(String id);

    User getUserByEmail(String email);

    User saveUser(User user);

    User updateUser(User user);

    HttpStatus deleteUser(String id);

}
