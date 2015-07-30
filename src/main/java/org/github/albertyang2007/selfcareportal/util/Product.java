package org.github.albertyang2007.selfcareportal.util;

import java.io.Serializable;

public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2054849840979043764L;

	private String productNo;

	private String description;

	private Double price;

	private boolean enabled;

	private static final int ODD_NUM = 7;

	public Product() {

	}

	public Product(String productNo, String description, Double price,
			boolean enabled) {
		this.productNo = productNo;
		this.description = description;
		this.price = price;
		this.enabled = enabled;
	}

	public String toString() {
		return this.productNo + ";" + this.description + ";" + this.price + ";"
				+ this.enabled;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		return productNo.hashCode() * ODD_NUM;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Product)) {
			return false;
		}

		if (getClass() != o.getClass()) {
			return false;
		}
		Product compared = (Product) o;
		return compared.productNo == this.productNo;
	}

}
