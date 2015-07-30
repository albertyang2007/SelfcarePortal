package org.github.albertyang2007.selfcareportal.repository.service;

import java.util.List;

import org.github.albertyang2007.selfcareportal.repository.entity.MyOrder;
import org.github.albertyang2007.selfcareportal.repository.entity.User;
import org.github.albertyang2007.selfcareportal.util.Product;

public interface DataAccessService {
	
	User addUser(String userId, String password, String nickName);
	
	User findUser(String userId);
	
	boolean delUser(String userId);
	
	boolean delOrderById(Integer orderId);
	
	List<User> findAllUsers();
	
	MyOrder addOrder(String userId, List<Product> products);
	
	MyOrder findOrderById(Integer orderId);
	
	List<MyOrder> findOrders(String userId);
	
	List<MyOrder> findOrdersbypage(String userId,int start,int number);
	
    long findOrdersNum(String userId);
}
