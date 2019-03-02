package com.devglan.service;

import com.devglan.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String>{

    Authority findByName(String name);

}