package com.ifood.nutritional.application.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;

import com.ifood.nutritional.domain.aggregator.NutritionalApproximateFood;
import com.ifood.nutritional.domain.aggregator.NutritionalSpecificFood;
import com.ifood.nutritional.domain.model.Garnish;
import com.ifood.nutritional.domain.model.Meal;
import com.ifood.nutritional.domain.model.OrderItems;
import com.ifood.nutritional.domain.model.OrderNutritional;
import com.ifood.nutritional.domain.model.taco.Food;
import com.ifood.nutritional.domain.service.MealService;
import com.ifood.nutritional.domain.service.NutritionalService;
import com.ifood.nutritional.structure.repository.MealSpecifications;

@RunWith(MockitoJUnitRunner.class)
public class NutritionalControllerTest {
		
	private NutritionalSpecificFood nutricionalSpecific;

	private NutritionalApproximateFood nutricionalApproximate;

	private MealService mealService;
	
	private NutritionalService nutritionalAPI;
	
	public NutritionalControllerTest() {
		this.nutritionalAPI = Mockito.mock(NutritionalService.class, Mockito.RETURNS_DEEP_STUBS);
		this.mealService = Mockito.mock(MealService.class, Mockito.RETURNS_DEEP_STUBS);
		this.nutricionalSpecific = new NutritionalSpecificFood(mealService, nutritionalAPI);
		this.nutricionalApproximate = new NutritionalApproximateFood(mealService, nutritionalAPI);
	}

	public List<Meal> allMeals() {
		return Arrays.asList(
				newMeal(10.00,100.00,1,"Marmitex pequena", newGarnishesMarmitex(100.00)), 
				newMeal(19.00,200.00,2,"Marmitex grande", newGarnishesMarmitex(200.00)),
				newMeal(15.00, 50.00, 3, "Acai", newGarnishesAcai(150.00))
				);
	}
	
	private Meal newMeal(Double price, Double quantity, int code, String description, Set<Garnish> garnishes) {
		return new Meal.ImmutableMeal()
				.withtPrice(new BigDecimal(9.00))
				.withIdMeal(code)
				.withTitle(description)
				.isAvailable(Boolean.TRUE)
				.addAllGarnishes(garnishes)
				.build();
	}
	private Set<Garnish> newGarnishesMarmitex(Double quantity) {
		HashSet<Garnish> set = new HashSet<>();
		set.add(newGarnish("Arroz", 1.00, quantity));
		set.add(newGarnish("Feijão", 1.50,quantity));
		set.add(newGarnish("Ovo", 0.80, quantity));
		set.add(newGarnish("Frango", 5.00, quantity));
		return set;
	}
	
	private Set<Garnish> newGarnishesAcai(Double quantity) {
		HashSet<Garnish> set = new HashSet<>();
		set.add(newGarnish("Morango", 1.00, quantity));
		set.add(newGarnish("Manga", 1.50,quantity));
		set.add(newGarnish("Granola", 0.80, quantity));
		set.add(newGarnish("Kiwi", 5.00, quantity));
		return set;
	}

	private Garnish newGarnish(String description, Double price, Double quantity) {
		return new Garnish.ImmutableGarnish()
		.withName(description)
		.withPrice(new BigDecimal(price))
		.withQuantity(new BigDecimal(quantity))
		.withAvailable(Boolean.TRUE)
		.build();
	}
	
	private OrderNutritional newOrderNutritional(Boolean specificFood, int code) {
		return new OrderNutritional.ImmutableOrderNutritional()
				.withSpecificFood(specificFood)
				.addOrderItems(Collections.singletonList(
						new OrderItems.ImmutableOrderDescription()
						.withCode(code)
						.build()))
				.build();
	}
	
	private OrderNutritional newOrderNutritionalTwoOptions(Boolean specificFood, int firstCode, int secondCode, int kcal) {
		return new OrderNutritional.ImmutableOrderNutritional()
				.withSpecificFood(specificFood)
				.withKcal(kcal)
				.addOrderItems(Arrays.asList(
						new OrderItems.ImmutableOrderDescription()
						.withCode(firstCode)
						.build(),
						new OrderItems.ImmutableOrderDescription()
						.withCode(secondCode)
						.build()
						))
				.build();
	}
	
	private Food newFood(String description, int code, int category, double kcal) {
		return new Food.ImmutableFood()
				.withCategoryId(category)
				.withId(code)
				.withDescription(description)
				.withBaseQty(100)
				.withEnergyKcal(kcal)
				.build();
	}
	

	private List<Food> marmitexFoodsApi() {
		return Arrays.asList(newFood("Arroz", 1, 1, 120), newFood("Feijão, carioca", 2, 9, 80));
	}
	
	private List<Food> allFoodsApi() {
		return Arrays.asList(newFood("Arroz", 1, 1, 120), newFood("Feijão, carioca", 2, 9, 80), newFood("Manga", 228, 3, 90));
	}
	
	@Test
	public void especificFood() {
		Specification<Meal> spec = MealSpecifications.search(any());
		List<Meal> allMeals = allMeals().stream().filter(meal -> meal.getGarnish().stream().map(Garnish::getNameUpperCase).collect(Collectors.toList()).contains("ARROZ")).collect(Collectors.toList());
		when(mealService.findAll(spec)).thenReturn(allMeals);
		when(nutritionalAPI.searchListFood(any())).thenReturn(marmitexFoodsApi());
		
		List<Meal> food = nutricionalSpecific.find(newOrderNutritional(true, 1));
		
		assertThat(food.stream().count()).isEqualTo(allMeals.stream().filter(meal -> meal.getGarnish().stream().map(Garnish::getNameUpperCase).collect(Collectors.toList()).contains("ARROZ")).count());
	}
	
	@Test
	public void especificFoodNotFound() {
		Specification<Meal> spec = MealSpecifications.search(any());
		when(mealService.findAll(spec)).thenReturn(Collections.emptyList());
		when(nutritionalAPI.searchListFood(any())).thenReturn(marmitexFoodsApi());
		
		List<Meal> food = nutricionalSpecific.find(newOrderNutritional(true, 10));
		
		Assert.assertTrue(food.isEmpty());
	}
	
	@Test
	public void nutricionalSimilar() {
		List<Meal> allMeals = allMeals();
		when(mealService.searchMealsRestauntActive()).thenReturn(allMeals);
		when(nutritionalAPI.searchFoodsCategory(any())).thenReturn(marmitexFoodsApi());
		when(nutritionalAPI.searchListFood(any())).thenReturn(marmitexFoodsApi());

		List<Meal> food = nutricionalApproximate.find(newOrderNutritional(false, 1));
		
		assertThat(food.stream().count())
				.isEqualTo(allMeals.stream().filter(meal -> meal.getGarnish().stream().map(Garnish::getNameUpperCase).collect(Collectors.toList()).contains("ARROZ")).count());
	}
	
	@Test
	public void nutricionalSimilarButNotFoundOneCategory() {
		List<Meal> allMeals = allMeals();
		when(mealService.searchMealsRestauntActive()).thenReturn(allMeals);
		when(nutritionalAPI.searchFoodsCategory(any())).thenReturn(allFoodsApi());
		when(nutritionalAPI.searchListFood(any())).thenReturn(allFoodsApi());

		List<Meal> food = nutricionalApproximate.find(newOrderNutritionalTwoOptions(false, 1, 228, 0));
		
		Assert.assertTrue(food.isEmpty());
	}
	
	@Test
	public void nutricionalWithKcalHit() {
		List<Meal> allMeals = allMeals().stream().filter(meal -> meal.getGarnish().stream().map(Garnish::getNameUpperCase).collect(Collectors.toList()).contains("ARROZ")).collect(Collectors.toList());
		when(mealService.searchMealsRestauntActive()).thenReturn(allMeals);
		when(nutritionalAPI.searchFoodsCategory(any())).thenReturn(marmitexFoodsApi());
		when(nutritionalAPI.searchListFood(any())).thenReturn(marmitexFoodsApi());
		
		List<Meal> food = nutricionalApproximate.find(newOrderNutritionalTwoOptions(false, 1, 2, 200));
		
		assertThat(food.stream().count())
			.isEqualTo(allMeals.stream().filter(meal -> meal.getGarnish().stream().map(Garnish::getNameUpperCase).collect(Collectors.toList()).contains("ARROZ")).count());
	}
	
	@Test
	public void nutricionalWithKcalNotHit() {
		List<Meal> allMeals = allMeals();
		when(mealService.searchMealsRestauntActive()).thenReturn(allMeals);
		when(nutritionalAPI.searchFoodsCategory(any())).thenReturn(allFoodsApi());
		when(nutritionalAPI.searchListFood(any())).thenReturn(allFoodsApi());

		List<Meal> food = nutricionalApproximate.find(newOrderNutritionalTwoOptions(false, 1, 2, 300));
		
		assertThat(food.stream().count()).isEqualTo(0);
	}
	
}
