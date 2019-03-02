package com.ifood.nutritional.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifood.nutritional.structure.exception.MealInvalidException;
import com.ifood.nutritional.structure.util.StringUtil;
import com.ifood.nutritional.structure.util.UtilValidation;

@Entity
@Table(name = "meal")
@JsonIgnoreProperties(value = { "descriptionUpperCase" })
public class Meal implements Serializable, Comparable<Meal> {
	
	private static final long serialVersionUID = 91384728914837L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idMeal;
	
    @NotEmpty(message = "Title required")
	private String title;
	
	private String description;
    
    @NotEmpty(message= "available required")
    private Boolean available;
    
    @NotEmpty(message="Price required")
    private BigDecimal price;
  
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_meal")
    private Set<Garnish> garnish;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRestaurant")
    private Restaurant restaurant;
    
    public Meal() {}

    public Meal(Integer idMeal, String title, String description, Boolean available, BigDecimal price,
			Set<Garnish> garnish) {
		super();
		this.idMeal = idMeal;
		this.title = title;
		this.description = description;
		this.available = available;
		this.price = price;
		this.garnish = garnish;
	}


	public Integer getIdMeal() {
		return idMeal;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
	
	@JsonProperty
	public String getDescriptionUpperCase() {
		return description != null ? StringUtil.removeAccents(description.toUpperCase()) : description;
	}

	public Boolean getAvailable() {
		return available;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Set<Garnish> getGarnish() {
		return garnish;
	}

	private void setIdMeal(Integer idMeal) {
		this.idMeal = idMeal;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	private void setAvailable(Boolean available) {
		this.available = available;
	}

	private void setPrice(BigDecimal price) {
		this.price = price;
	}

	private void setGarnish(Set<Garnish> garnish) {
		this.garnish = garnish;
	}

	public static class ImmutableMeal{
    	
    	private Meal meal = new Meal();
    	
    	public Meal build() {
    		return meal;
    	}

    	public ImmutableMeal withIdMeal(Integer idMeal) {
    		if(UtilValidation.isNull(idMeal)) {
    			throw new MealInvalidException("Id required");
    		}
    		meal.setIdMeal(idMeal);
    		return this;
    	}

    	public ImmutableMeal withTitle(String title) {
    		if(UtilValidation.isNull(title)) {
    			throw new MealInvalidException("Title required");
    		}
    		meal.setTitle(title);
    		return this;
    	}

    	public ImmutableMeal withDescription(String description) {
    		meal.setDescription(description);
    		return this;
    	}

    	public ImmutableMeal isAvailable(Boolean available) {
    		if(UtilValidation.isNull(available)) {
    			throw new MealInvalidException("Available required");
    		}
    		meal.setAvailable(available);
    		return this;
    	}

    	public ImmutableMeal withtPrice(BigDecimal price) {
    		if(UtilValidation.isNull(price)) {
    			throw new MealInvalidException("Price required");
    		}
    		meal.setPrice(price);
    		return this;
    	}

    	public ImmutableMeal addGarnishes(Garnish garnish) {
    		if(UtilValidation.isNull(meal.getGarnish())) {
    			meal.setGarnish(new HashSet<>());
    		}
    		if(UtilValidation.isNull(meal.getGarnish())) {
    			throw new MealInvalidException("Garnish required");
    		}
    		meal.getGarnish().add(garnish);
    		return this;
    	}
    	
    	public ImmutableMeal addAllGarnishes(Set<Garnish> garnish) {
    		if(UtilValidation.isNull(garnish)) {
    			throw new MealInvalidException("Garnish required");
    		}
    		meal.setGarnish(garnish);
    		return this;
    	}
    }

	@Override
	public int compareTo(Meal o) {
		return this.idMeal < o.idMeal ? -1 : (this.idMeal < o.idMeal) ? 0 : 1;
	}	
}
