package com.ifood.nutritional.domain.aggregator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ifood.nutritional.domain.model.Garnish;
import com.ifood.nutritional.domain.model.Meal;
import com.ifood.nutritional.domain.model.OrderItems;
import com.ifood.nutritional.domain.model.OrderNutritional;
import com.ifood.nutritional.domain.model.taco.Food;
import com.ifood.nutritional.domain.service.MealService;
import com.ifood.nutritional.domain.service.NutritionalService;
import com.ifood.nutritional.structure.util.StringUtil;

@Component
public class NutritionalFood {
	
	@Autowired
	protected MealService mealService;
	
	
	@Autowired
	protected NutritionalService nutritionalServiceAPI;
	
	
	public static <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
	    Map<Object,String> seen = new ConcurrentHashMap<Object, String>();
	    return t -> seen.put(keyExtractor.apply(t), "") == null;
	}

	public List<String> getNameGarnishes(Meal meal) {
		return meal.getGarnish()
				.stream()
				.filter(Garnish::getAvailable)
				.map(Garnish::getNameUpperCase)
				.map(StringUtil::removeAccents)
				.distinct()
				.collect(Collectors.toList());
	}

	public int[] orderItemsByCode(OrderNutritional orderDescription) {
		return orderDescription.getOrderItems().stream()
				.mapToInt(OrderItems::getCode)
				.distinct()
				.toArray();
	}
	
	public int[] orderItemByIdCategory(List<Food> listFood) {
		return listFood.stream()
				.mapToInt(Food::getCategory_id)
				.distinct()
				.toArray();
	}
	
	public Food handleDescription(Food o) {
		o.setDescription(o.getDescription().split(",")[0]);
		return o;
	}

}
