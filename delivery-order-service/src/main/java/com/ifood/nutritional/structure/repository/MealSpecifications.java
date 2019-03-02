package com.ifood.nutritional.structure.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.ifood.nutritional.domain.model.Garnish;
import com.ifood.nutritional.domain.model.Meal;
import com.ifood.nutritional.domain.model.Restaurant;

public final class MealSpecifications {
	 
    public static Specification<Meal> search(List<String> descriptionMeal) {
        return (root,  query,  cb) -> {		        
        	Join<Meal, Garnish> join = root.join("garnish", JoinType.LEFT);
        	Join<Meal, Restaurant> joinRestaurant = root.join("restaurant", JoinType.INNER);
        	List<Predicate> predicatesName = descriptionMeal.stream()
        			.map(g -> cb.like(root.get("description"), "%" + g + "%")).collect(Collectors.toList());
        	predicatesName.addAll(descriptionMeal.stream()
        			.map(g -> cb.like(join.get("name"), "%" + g + "%")).collect(Collectors.toList()));
        	
        	Predicate activeGarnish = cb.equal(join.get("available"), 1);
        	Predicate activeRestaurant = cb.equal(joinRestaurant.get("open"), 1);
        	Predicate activeMeal = cb.equal(root.get("available"), 1);
        	
        	query.groupBy(root.get("idMeal"));
        	
        	return cb.and(activeGarnish, activeRestaurant, activeMeal, cb.or(predicatesName.toArray(new Predicate[predicatesName.size()])));
		};
    }
}
