package com.ifood.nutritional.structure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ifood.nutritional.domain.model.Meal;

public interface MealRepository extends JpaRepository<Meal, Integer>, JpaSpecificationExecutor<Meal> {

	@Query(value="select m.* from garnish g "
			+ "right join meal m on m.id_meal = g.id_meal "
			+ "inner join restaurant r on r.id_restaurant=m.id_restaurant "
			+ "where r.open = 1 and m.available = 1 and g.available = 1 group by m.id_meal", nativeQuery=true)
	public List<Meal> searchMealsRestauntActive();
	
}
