package com.ifood.nutritional.domain.aggregator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ifood.nutritional.domain.model.Garnish;
import com.ifood.nutritional.domain.model.Meal;
import com.ifood.nutritional.domain.model.OrderNutritional;
import com.ifood.nutritional.domain.model.taco.Food;
import com.ifood.nutritional.domain.service.MealService;
import com.ifood.nutritional.domain.service.NutritionalService;

@Component
public class NutritionalApproximateFood extends NutritionalFood {
	
	public NutritionalApproximateFood(MealService mealService, NutritionalService nutritionalService) {
		this.mealService=mealService;
		this.nutritionalServiceAPI=nutritionalService;
	}
	
	/***
	 * Returns meals that have garnishes with equals category or similar what is required,
	 *  but return nothing if some category is not found
	 * 
	 * @return meal list
	 * */
	public List<Meal> find(OrderNutritional orderDescription){
		List<Food> foodsAPI = searchFoodsAPI(orderDescription);
		List<Food> allFoodByCategoryAPI = allFoodsByCategory(foodsAPI);
		List<Meal> meals = mealService.searchMealsRestauntActive();
		Map<Meal, List<Food>> mealsContainsCategory = garnishesGroupByCategory(meals, allFoodByCategoryAPI);
		int categoryNumber = categoryNumber(allFoodByCategoryAPI);
		return orderDescription.getKcal() > 0 
				? countKcal(mealsContainsCategory, orderDescription, categoryNumber)
				: mealsContainsCategory
					.entrySet()
					.stream()
					.filter(foods -> categoryNumber(foods.getValue()) == categoryNumber)
					.filter(o -> o.getValue().size() > 0)
					.map(Entry::getKey)
					.collect(Collectors.toList());
	}
	
	private List<Food> allFoodsByCategory(List<Food> listFoodsAPI) {
		return nutritionalServiceAPI.searchFoodsCategory(orderItemByIdCategory(listFoodsAPI))
				.stream()
				.map(o ->  handleDescription(o))
				.filter(distinctByKey(Food::getDescription))
				.collect(Collectors.toList());
	}

	private List<Food> searchFoodsAPI(OrderNutritional orderDescription) {
		return nutritionalServiceAPI.searchListFood(orderItemsByCode(orderDescription))
				.stream()
				.map(o ->  handleDescription(o))
				.distinct()
				.collect(Collectors.toList());
	}

	private Map<Meal, List<Food>> garnishesGroupByCategory(List<Meal> meals, List<Food> category) {
		return meals
				.stream()
				.collect(Collectors
						.toMap(Function.identity(), meal -> {
							List<String> nameGarnishes = getNameGarnishes(meal);
							List<Food> foods = category.stream()
									.filter(categoryFood -> nameGarnishes.stream()
										.filter(garnish -> garnishContainsCategory(categoryFood, garnish))
										.findFirst()
										.isPresent())
									.collect(Collectors.toList());
							return foods;
						}));
	}

	private boolean garnishContainsCategory(Food categoryFood, String garnish) {
		return garnish.trim().indexOf(categoryFood.getDescriptionUpperCase()) >= 0;
	}
	
	private List<Meal> countKcal(Map<Meal, List<Food>> mealsContainsCategory, OrderNutritional orderDescription, int categoryNumber){
		return mealsContainsCategory
				.entrySet()
				.stream()
				.filter(foods -> categoryNumber(foods.getValue()) == categoryNumber)
				.filter(food -> kcalByMeal(food.getValue(), food.getKey()) >= orderDescription.getKcal())
				.map(Entry::getKey)
				.collect(Collectors.toList());
	}

	private int categoryNumber(List<Food> allFoodByCategoryAPI) {
		int numberOfCategory = allFoodByCategoryAPI.stream().collect(Collectors.groupingBy(Food::getCategory_id)).size();
		return numberOfCategory;
	}
	
	private double kcalByMeal(List<Food> foodFound, Meal meal) {
		Map<Integer, List<Food>> groupByCategory = foodFound
				.stream()
				.collect(Collectors.groupingBy(Food::getCategory_id));
		return sumKcal(meal, groupByCategory);
	}
	
	private double sumKcal(Meal meal, Map<Integer, List<Food>> groupByCategory) {
		return groupByCategory.entrySet()
		.stream()
		.mapToDouble(foodByCategory  -> countKcalByQuantity(meal, foodByCategory.getValue()))
		.sum();
	}

	private double countKcalByQuantity(Meal meal, List<Food> category) {
		return meal.getGarnish()
				.stream()
				.mapToDouble(garnish -> countByGarnishes(category, garnish))
				.sum();
	}

	private double countByGarnishes(List<Food> categorys, Garnish garnish) {
		if(garnish.getQuantity() == null 
				|| garnish.getQuantity().doubleValue() == 0) {
			return 0;
		}
		return categorys.stream()
				.filter(category -> findGarnishesByCategory(garnish, category))
				.mapToDouble(c -> sumKcalByGarnish(c, garnish))
				.sum();
	}
	
	private boolean findGarnishesByCategory(Garnish garnish, Food c) {
		return garnish.getNameUpperCase().contains(c.getDescriptionUpperCase());
	}
	
	private double sumKcalByGarnish(Food category, Garnish g) {
		return new BigDecimal(category.getAttributes().getEnergy().getKcal())
				.multiply(g.getQuantity())
				.divide(new BigDecimal(category.getBase_qty()))
				.doubleValue();
	}

}
