package com.ifood.nutritional.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ifood.nutritional.domain.aggregator.NutritionalAggregator;
import com.ifood.nutritional.domain.model.Meal;
import com.ifood.nutritional.domain.model.OrderNutritional;

@RestController()
@RequestMapping(value = "/api/nutritional", produces = MediaType.APPLICATION_JSON_VALUE)
public class NutriticionalController {
	
	@Autowired
	public NutritionalAggregator nutritionalAggregator;
	
    @RequestMapping(value = "/serchDiet", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
	public ResponseEntity<List<Meal>> searchDiet(@RequestBody OrderNutritional orderNutritional){
		return ResponseEntity.ok(nutritionalAggregator.matchBetterMeal(orderNutritional));
	}

}
