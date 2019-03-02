package com.ifood.nutritional.domain.model.taco;

import com.ifood.nutritional.structure.util.StringUtil;

public class Food {

    private int id;
    private String description;
    private int base_qty;
    private String base_unit;
    private int category_id;
    private Attributes attributes;
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setDescription(String description) {
       this.description = description;
    }
    public String getDescription() {
       return description;
    }
    public String getDescriptionUpperCase() {
        return StringUtil.removeAccents(description.toUpperCase());
    }

    public int getBase_qty() {
		return base_qty;
	}
	public void setBase_qty(int base_qty) {
		this.base_qty = base_qty;
	}
	public String getBase_unit() {
		return base_unit;
	}
	public void setBase_unit(String base_unit) {
		this.base_unit = base_unit;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public void setAttributes(Attributes attributes) {
         this.attributes = attributes;
     }
     public Attributes getAttributes() {
         return attributes;
     }

     public static class ImmutableFood{
    	 private Food food = new Food();
    	 private Attributes attributes = new Attributes();
    	 
    	 public Food build() {
    		 this.food.setAttributes(attributes);
    		 return this.food;
    	 }
    	 
    	 public ImmutableFood withId(int id) {
    		 this.food.setId(id);
    		 return this;
    	 }
    	 
    	 public ImmutableFood withDescription(String description) {
    		 this.food.setDescription(description);
    		 return this;
    	 }
    	 
    	 public ImmutableFood withBaseQty(int baseQty) {
    		 this.food.setBase_qty(baseQty);
    		 return this;
    	 }
    	 
    	 public ImmutableFood withCategoryId(int categoryId) {
    		 this.food.setCategory_id(categoryId);
    		 return this;
    	 }
    	 
    	 public ImmutableFood withEnergyKcal(double kcal) {
    		 if(attributes.getEnergy() == null) {    	
    			 this.attributes.setEnergy(new Energy());
    		 }
    		 this.attributes.getEnergy().setKcal(kcal);
    		 return this;
    	 }
    	 
     }
}