package com.ifood.nutritional.domain.model;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.ifood.nutritional.structure.exception.OrderNutritionalException;

public class OrderNutritional {
	
	@NotEmpty
	private Boolean specificFood;
	
	@NotEmpty
	private List<OrderItems> orderItems;
	
	private int kcal;	

	private OrderNutritional() {
		super();
	}

	public Boolean getSpecificFood() {
		return specificFood;
	}

	public List<OrderItems> getOrderItems() {
		return orderItems;
	}

	public int getKcal() {
		return kcal;
	}

	
	public static class ImmutableOrderNutritional{
		
		private OrderNutritional orderNutritional = new OrderNutritional();
		
		public OrderNutritional build() {
			return orderNutritional;
		}
		
		public ImmutableOrderNutritional withSpecificFood(Boolean specificFood) {
			this.orderNutritional.specificFood =  specificFood;
			return this;
		}

		public ImmutableOrderNutritional withKcal(int kcal) {
			this.orderNutritional.kcal = kcal;
			return this;
		}
		
		public ImmutableOrderNutritional addOrderItems(List<OrderItems> orderItems) {
			if(orderNutritional == null) {
				throw new OrderNutritionalException("Order items required");
			}
			this.orderNutritional.orderItems =  orderItems;
			return this;
		}
	}
}
