package com.devglan.dao;

import com.devglan.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    Optional<User>  findByUsername(String username);
}
