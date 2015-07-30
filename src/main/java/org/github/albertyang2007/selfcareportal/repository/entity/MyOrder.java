package org.github.albertyang2007.selfcareportal.repository.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MyOrders")
public class MyOrder {

	private Integer orderId;
	private User user;
	private Date createDate;
	private List<Item> products;
	private static final int UNINIT_ORDER_HASHCODE=0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@ManyToOne
	@JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	public List<Item> getProducts() {
		return products;
	}

	public void setProducts(List<Item> products) {
		this.products = products;
	}

	@Override
	public int hashCode() {
		if(orderId==null){
			return UNINIT_ORDER_HASHCODE;
		}
    	return orderId;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MyOrder)) {
			return false;
		}
		MyOrder compared = ((MyOrder) o);
		if (orderId == null) {
			return compared.getOrderId() == null;
		}
		return orderId.equals(compared.getOrderId());
	}

	@Override
	public String toString() {
		String str = "OrderId: " + orderId + " User: " + " Date:" + createDate
				+ " product list:\n";
		for (Item i : products) {
			str += i + "\n";
		}
		return str;
	}

}
