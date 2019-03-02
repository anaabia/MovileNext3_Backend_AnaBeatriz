package com.ifood.nutritional.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifood.nutritional.structure.exception.GarnishInvalidException;
import com.ifood.nutritional.structure.util.StringUtil;
import com.ifood.nutritional.structure.util.UtilValidation;

@Entity
@Table(name = "garnish")
@JsonIgnoreProperties(value = { "nameUpperCase" })
public class Garnish implements Serializable {
	
	private static final long serialVersionUID = 1923984820458L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idGarnish;
	
    @NotEmpty(message = "Name required")
	private String name;
	
    @NotEmpty(message = "Available required")
	private Boolean available;
	
	private BigDecimal price;
	
	private BigDecimal quantity;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_meal")
	private Meal meal;
	 
	private Garnish() {}

	private void setIdGarnish(Integer idGarnish) {
		this.idGarnish = idGarnish;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setAvailable(Boolean available) {
		this.available = available;
	}

	private void setPrice(BigDecimal price) {
		this.price = price;
	}

	private void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
 
	 public Integer getIdGarnish() {
		return idGarnish;
	}

	public String getName() {
		return name;
	}

	public Boolean getAvailable() {
		return available;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public String getNameUpperCase() {
		return StringUtil.removeAccents(name.toUpperCase());
	}
	
	public static class ImmutableGarnish{
		
		private Garnish garnish = new Garnish();
		 
		public Garnish build() {
			return garnish;
		}
		
		public ImmutableGarnish withIdGarnish(Integer idGarnish) {
    		if(UtilValidation.isNull(idGarnish)) {
    			throw new GarnishInvalidException("IdGarnish required");
    		}
			this.garnish.setIdGarnish(idGarnish);
			return this;
		}
		
		public ImmutableGarnish withName(String name) {
    		if(UtilValidation.isNull(name)) {
    			throw new GarnishInvalidException("Name required");
    		}
			this.garnish.setName(name);
			return this;
		}
		
		public ImmutableGarnish withAvailable(Boolean available) {
    		if(UtilValidation.isNull(available)) {
    			throw new GarnishInvalidException("Available required");
    		}
			this.garnish.setAvailable(available);
			return this;
		}
		
		public ImmutableGarnish withPrice(BigDecimal price) {
			this.garnish.setPrice(price);
			return this;
		}
		
		public ImmutableGarnish withQuantity(BigDecimal quantity) {
			this.garnish.setQuantity(quantity);
			return this;
		}
	 }
}
