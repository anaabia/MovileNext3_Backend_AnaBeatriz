package com.ifood.nutritional.structure.repository;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ifood.nutritional.domain.model.taco.Food;

@Service
@FeignClient(url="${taco-api.url}", name="${taco-api.name}")
public interface NutritionalRepository {
	
    @GetMapping("food/{foodId}")
	public List<Food> searchFood(@PathVariable("foodId")int foodId);
    
    @GetMapping("/food/list/{listCodes}")
    public List<Food> searchListFood(@PathVariable("listCodes")int[] listCodes);
    
    @GetMapping("/food/category/list/{listCategoryId}")
    public List<Food> searchFoodsCategory(@PathVariable("listCategoryId")int[] listCategoryId);

}
