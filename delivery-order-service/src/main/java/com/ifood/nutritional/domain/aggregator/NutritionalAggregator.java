package com.ifood.nutritional.domain.aggregator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ifood.nutritional.domain.model.Meal;
import com.ifood.nutritional.domain.model.OrderNutritional;

@Component
public class NutritionalAggregator {
	
	@Autowired
	private NutritionalApproximateFood approximateFood;
	
	@Autowired
	private NutritionalSpecificFood specificFood;
	
	public List<Meal> matchBetterMeal(OrderNutritional orderDescription){
		return orderDescription.getSpecificFood().booleanValue() ? specificFood.find(orderDescription) : approximateFood.find(orderDescription);
	}

}
