package com.devglan.service;

import com.devglan.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(long id);
}
