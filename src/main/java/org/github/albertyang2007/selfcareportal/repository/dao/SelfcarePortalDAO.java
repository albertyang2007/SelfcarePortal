package org.github.albertyang2007.selfcareportal.repository.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.github.albertyang2007.selfcareportal.repository.entity.MyOrder;
import org.github.albertyang2007.selfcareportal.repository.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SelfcarePortalDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	public User findUser(String userId){
		return entityManager.find(User.class, userId);
	}
	
	public MyOrder findOrderByOrderId(Integer orderId){
		return entityManager.find(MyOrder.class, orderId);
	}
	
	public boolean delOrderByOrderId(Integer orderId){
		MyOrder order = entityManager.find(MyOrder.class, orderId);
		if(order!=null){
			entityManager.remove(order);
			return true;
		}
		return false;
	}
	
	public User addUser(User user){
		entityManager.persist(user);
		return user;
	}
	
	public boolean delUser(String userId){
		User user = entityManager.find(User.class, userId);
		if(user!=null){
			entityManager.remove(user);
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<MyOrder> findOrders(String userId){
			List<MyOrder> orders = entityManager.createQuery("select myorder from MyOrder myorder where myorder.user.userId=?1")
				.setParameter(1, userId).getResultList();
			if(orders.size()>0){
				return orders;
			}
			return null;
	}
	
	public MyOrder addOrder(MyOrder order){
		entityManager.persist(order);
		return order;
	}

	public List<User> findAllUsers() {		
		return entityManager.createQuery("select user from User user",
				User.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<MyOrder> findOrdersbypage(String userId,int start,int number){
		 
		List<MyOrder> ordersbypage = entityManager.createQuery("select myorder from MyOrder myorder where myorder.user.userId=?1")
				.setParameter(1, userId).setFirstResult(start).setMaxResults(number).getResultList();
		if(ordersbypage.size()>0){
			return ordersbypage;
		}
		return null;
    }
	
	public long findOrdersNum(String userId){
		return entityManager.createQuery("select count(myorder) from MyOrder myorder where myorder.user.userId=?1",Long.class)
				.setParameter(1, userId).getSingleResult();			     
	
	}
}
