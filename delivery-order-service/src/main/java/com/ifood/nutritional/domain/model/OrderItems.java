package com.ifood.nutritional.domain.model;

import org.hibernate.validator.constraints.NotEmpty;

public class OrderItems {
	
	@NotEmpty
	private int code;
	
	@NotEmpty
	private double quantity;
	
	@NotEmpty
	private String unit;
		
	private OrderItems() {}

	public int getCode() {
		return code;
	}

	public double getQuantity() {
		return quantity;
	}

	public String getUnit() {
		return unit;
	}

	public static class ImmutableOrderDescription{
		private OrderItems order = new OrderItems();
		
		public OrderItems build() {
			return this.order;
		}
		
		public ImmutableOrderDescription withCode(int code) {
			this.order.code = code;
			return this;
		}

		public ImmutableOrderDescription withQuantity(double quantity) {
			this.order.quantity = quantity;
			return this;
		}

		public ImmutableOrderDescription withUnit(String unit) {
			this.order.unit = unit;
			return this;
		}
	}
}
