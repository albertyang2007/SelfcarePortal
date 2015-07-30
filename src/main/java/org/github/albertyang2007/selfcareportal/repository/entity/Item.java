package org.github.albertyang2007.selfcareportal.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Items")
public class Item{

	private Integer productId;
	private String productNo;
	private String description;
	private Double price;
	
	private static final int UNINIT_ITEM_HASHCODE=0;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Override
    public int hashCode()
    {
		if(productId==null){
			return UNINIT_ITEM_HASHCODE;
		}
    	return productId;
    }
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Item)){
			return false;
		}
		Item compared = (Item)o;
		if(productId==null){
			return compared.getProductId()==null;
		}
		return productId.equals(compared.getProductId());
	}
	
	
	@Override
	public String toString(){
		return "ProductId: "+productId+" ProductNo:"+productNo+" Description: "+description+" Price: "+price;
	}

}

