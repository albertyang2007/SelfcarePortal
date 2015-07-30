package org.github.albertyang2007.selfcareportal.repository.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.github.albertyang2007.selfcareportal.repository.dao.SelfcarePortalDAO;
import org.github.albertyang2007.selfcareportal.repository.entity.*;
import org.github.albertyang2007.selfcareportal.util.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataAccessServiceImpl implements DataAccessService{

	@Autowired
	private SelfcarePortalDAO selfcarePortalDAO;
	
	@Transactional

	public User addUser(String userId, String password, String nickName){
		
		if(findUser(userId)!=null){
			return null;
		}
		User user = new User();
		user.setUserId(userId);
		user.setPassword(password);
		user.setNickName(nickName);
		return selfcarePortalDAO.addUser(user);	
	}
	
	@Transactional
	public User findUser(String userId){
		return selfcarePortalDAO.findUser(userId);
	}
	
	@Transactional
	public List<User> findAllUsers(){
		return selfcarePortalDAO.findAllUsers();
	}
	
	@Transactional
	public MyOrder addOrder(String userId, List<Product> products){
		MyOrder order = new MyOrder();
		List<Item> hProducts = new ArrayList<Item>();
		User user = findUser(userId);
		if(user==null){
			return null;
		}
		order.setUser(user);
		Date createDate = new Date();
		order.setCreateDate(createDate);
		Item item;
		for(Product p:products){
			item = new Item();
			item.setDescription(p.getDescription());
			item.setProductNo(p.getProductNo());
			item.setPrice(p.getPrice());
			hProducts.add(item);
		}
		order.setProducts(hProducts);
		return selfcarePortalDAO.addOrder(order);
	}
	
	@Transactional
	public List<MyOrder> findOrders(String userId){
		return selfcarePortalDAO.findOrders(userId);
	}

	@Transactional
	public MyOrder findOrderById(Integer orderId) {
		return selfcarePortalDAO.findOrderByOrderId(orderId);
	}

	@Transactional
	public boolean delUser(String userId) {		
		return selfcarePortalDAO.delUser(userId);
	}

	@Transactional
	public boolean delOrderById(Integer orderId) {		
		return selfcarePortalDAO.delOrderByOrderId(orderId);
	}
	
	@Transactional
	public List<MyOrder> findOrdersbypage(String userId,int start,int number){
		return selfcarePortalDAO.findOrdersbypage(userId, start, number);
	}
	
	@Transactional
	public long findOrdersNum(String userId){
		return selfcarePortalDAO.findOrdersNum(userId);
	}
}
