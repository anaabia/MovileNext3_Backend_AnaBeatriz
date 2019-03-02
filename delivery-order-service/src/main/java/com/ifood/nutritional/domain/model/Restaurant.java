package com.ifood.nutritional.domain.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.ifood.nutritional.structure.exception.RestaurantInvalidException;
import com.ifood.nutritional.structure.util.UtilValidation;

@Entity
@Table(name = "restaurant")
public class Restaurant implements Serializable {
	
	private static final long serialVersionUID = 1938173194303L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idRestaurant;
	
    @NotEmpty(message = "Name required")
	private String name;
    
    @NotEmpty(message = "Image required")
	private String UrlImage;
   
    @NotEmpty(message = "Open required")
    private Boolean open;

    @OneToMany
    @JoinColumn(name = "idRestaurant")
    private Set<Meal> meals;
    

    public Restaurant() {}

	public Integer getIdRestaurant() {
		return idRestaurant;
	}

	public String getName() {
		return name;
	}

	public Boolean getOpen() {
		return open;
	}
	
	public String getUrlImage() {
		return UrlImage;
	}

	public Set<Meal> getMeals() {
		return meals;
	}

	private void setIdRestaurant(Integer idRestaurant) {
		this.idRestaurant = idRestaurant;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setUrlImage(String urlImage) {
		UrlImage = urlImage;
	}

	private void setOpen(Boolean open) {
		this.open = open;
	}
	private void setMeals(Set<Meal> meals) {
		this.meals = meals;
	}

	public static class ImmutableRestaurant {
		
		private Restaurant restaurant = new Restaurant();
		
		public Restaurant build() {
			return this.restaurant;
		}

		public ImmutableRestaurant withIdRestaurant(Integer idRestaurant) {
			this.restaurant.setIdRestaurant(idRestaurant);
			return this;
		}

		public ImmutableRestaurant withName(String name) {
			this.restaurant.setName(name);
			return this;
		}

		public ImmutableRestaurant withUrlImage(String urlImage) {
			this.restaurant.setUrlImage(urlImage);
			return this;
		}
		
		public ImmutableRestaurant isOpen(Boolean open) {
			this.restaurant.setOpen(open);
			return this;
		}
		
		public ImmutableRestaurant addMeals(Meal meals) {
    		if(UtilValidation.isNull(restaurant.getMeals())) {
    			this.restaurant.setMeals(new HashSet<>());
    		}
			if(UtilValidation.isNull(meals)) {
				throw new RestaurantInvalidException("Meal required");
			}
			this.restaurant.getMeals().add(meals);
			return this;
		}

		public ImmutableRestaurant addAllMeals(Set<Meal> meals) {
			if(UtilValidation.isNull(meals)) {
				throw new RestaurantInvalidException("Meals required");
			}
			this.restaurant.setMeals(meals);
			return this;
		}
	}
}
