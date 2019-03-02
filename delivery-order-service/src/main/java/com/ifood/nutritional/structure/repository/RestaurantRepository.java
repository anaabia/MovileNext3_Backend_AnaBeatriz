package com.ifood.nutritional.structure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifood.nutritional.domain.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>{

}
