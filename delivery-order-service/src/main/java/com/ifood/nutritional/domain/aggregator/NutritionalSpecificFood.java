package com.ifood.nutritional.domain.aggregator;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.ifood.nutritional.domain.model.Meal;
import com.ifood.nutritional.domain.model.OrderNutritional;
import com.ifood.nutritional.domain.model.taco.Food;
import com.ifood.nutritional.domain.service.MealService;
import com.ifood.nutritional.domain.service.NutritionalService;
import com.ifood.nutritional.structure.repository.MealSpecifications;

@Component
public class NutritionalSpecificFood extends NutritionalFood {
	
	public NutritionalSpecificFood(MealService mealService, NutritionalService nutritionalServiceAPI) {
		this.mealService=mealService;
		this.nutritionalServiceAPI=nutritionalServiceAPI;
	}
	
	/**
	 * Return meals with exactly is required
	 * 
	 * @return meal list sorted according with more garnishes options found
	 * */
	public List<Meal> find(OrderNutritional orderDescription) {
		List<Food> listFood = findFoodsAPI(orderDescription);
		List<Meal> meals = findMeals(listFood);
		
		Map<Meal, Long> orderByMoreOptions = meals
				.stream()
				.collect(Collectors.toMap(meal -> meal, meal-> sumGanishsByMeal(meal, listFood)));
		
		orderByMoreOptions = orderByMoreOptions.entrySet().stream()
		  .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		  .collect(Collectors.toMap(
		    Map.Entry::getKey, 
		    Map.Entry::getValue, 
		    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		
		return orderByMoreOptions.keySet().stream().collect(Collectors.toList());
	}

	private List<Meal> findMeals(List<Food> listFood) {
		Specification<Meal> spec = MealSpecifications
				.search(
						listFood
						.stream()
						.map(food -> food.getDescription())
						.distinct()
						.collect(Collectors.toList()));
		List<Meal> meals =  mealService.findAll(spec);
		return meals;
	}

	private List<Food> findFoodsAPI(OrderNutritional orderDescription) {
		List<Food> listFood = nutritionalServiceAPI
				.searchListFood(orderItemsByCode(orderDescription))
				.stream()
				.map(food ->  handleDescription(food))
				.collect(Collectors.toList());
		return listFood;
	}
	
	private Long sumGanishsByMeal(Meal meal, List<Food> listFoods) {
		return countNumGarnishDescription(meal, listFoods) 
				+ countNumGarnish(meal, listFoods);
	}

	private long countNumGarnish(Meal meal, List<Food> listFoods) {
		List<String> nameGarnishes = getNameGarnishes(meal);
		return listFoods.stream()
				.map(Food::getDescriptionUpperCase)
				.distinct()
				.filter(food -> meal.getGarnish() != null && nameGarnishes.contains(food))
				.count();
	}

	private long countNumGarnishDescription(Meal meal, List<Food> listFoods) {
		return listFoods.stream()
				.map(Food::getDescriptionUpperCase)
				.distinct()
				.filter(food -> meal.getDescription() != null && meal.getDescriptionUpperCase().contains(food))
				.count();
	}
}
