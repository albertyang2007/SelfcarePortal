package org.github.albertyang2007.selfcareportal.repository;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.github.albertyang2007.selfcareportal.repository.entity.*;
import org.junit.Test;

public class EntityTest {	
	
	@Test
	public void testItemHash(){
		Set<Item> itemSet = new HashSet<Item>();
		Item item1 = new Item();
		item1.setDescription("iphone5");
		item1.setProductNo("1001");
		item1.setPrice(5555.0);
		
		Item item2 = new Item();
		item2.setDescription("iphone4");
		item2.setProductNo("1008");
		item2.setPrice(4444.0);
		
		Item item3 = new Item();
		item3.setProductId(111);
		item3.setDescription("iphone5");
		item3.setProductNo("1001");
		item3.setPrice(5555.0);
		
		Item item4 = new Item();
		item4.setProductId(111);
		item4.setDescription("iphone4");
		item4.setProductNo("1008");
		item4.setPrice(4444.0);
		
		Item item5 = new Item();
		item5.setProductId(119);
		item5.setDescription("iphone4");
		item5.setProductNo("1008");
		item5.setPrice(4444.0);
		
		itemSet.add(item1);
		itemSet.add(item2);
		itemSet.add(item3);
		itemSet.add(item4);
		itemSet.add(item5);
		itemSet.add(item1);
		
		assertThat(item1.equals(item2),is(true));
		assertThat(item3.equals(item4),is(true));
		assertThat(item1.equals(item3),is(false));
		assertThat(item4.equals(item5),is(false));
		assertEquals(itemSet.size(),3);		
	}

	@Test
	public void testUserHash(){
		Set<User> userSet = new HashSet<User>();
		User user1 = new User();
		user1.setUserId("ejiehta");
		user1.setNickName("Ten Tan");
		user1.setPassword("123456");
		User user2 = new User();
		user2.setUserId("ejiehta");
		user2.setNickName("Ten Tan2");
		user2.setPassword("1234567");
		User user3 = new User();
		user3.setUserId("abcdefg");
		user3.setNickName("Ten Tan");
		user3.setPassword("1234567");
		userSet.add(user1);
		userSet.add(user1);
		userSet.add(user2);
		userSet.add(user3);
		
		assertThat(user1.equals(user2),is(true));
		assertThat(user1.equals(user3),is(false));
		assertEquals(userSet.size(),2);		
	}
	
	@Test
	public void testMyOrderHash(){
		List<Item> itemSet1 = new ArrayList<Item>();
		List<Item> itemSet2 = new ArrayList<Item>();
		
		Item item1 = new Item();
		item1.setDescription("iphone5");
		item1.setProductNo("1001");
		item1.setPrice(5555.0);
		
		Item item2 = new Item();
		item2.setDescription("iphone6");
		item2.setProductNo("1002");
		item2.setPrice(6666.0);
		
		itemSet1.add(item1);
		itemSet2.add(item2);
		
		User user1 = new User();
		user1.setUserId("ejiehta");
		user1.setNickName("Ten Tan");
		user1.setPassword("123456");
		
		User user2 = new User();
		user2.setUserId("abcdefg");
		user2.setNickName("Ten Tan");
		user2.setPassword("123456");
		
		MyOrder order1 = new MyOrder();
		order1.setOrderId(999);
		order1.setUser(user1);
		order1.setProducts(itemSet1);
		order1.setCreateDate(new Date());
		
		MyOrder order2 = new MyOrder();
		order2.setUser(user1);
		order2.setProducts(itemSet1);
		order2.setCreateDate(new Date());
		
		MyOrder order3 = new MyOrder();
		order3.setOrderId(999);
		order3.setUser(user2);
		order3.setProducts(itemSet2);
		order3.setCreateDate(new Date());
		
		MyOrder order4 = new MyOrder();
		order4.setUser(user2);
		order4.setProducts(itemSet2);
		order4.setCreateDate(new Date());
		
		MyOrder order5 = new MyOrder();
		order5.setOrderId(222);
		order5.setUser(user2);
		order5.setProducts(itemSet2);
		order5.setCreateDate(new Date());
		
		Set<MyOrder> orderSet = new HashSet<MyOrder>();
		orderSet.add(order1);
		orderSet.add(order1);
		orderSet.add(order2);
		orderSet.add(order3);
		orderSet.add(order4);
		orderSet.add(order5);

		assertThat(order1.equals(order3),is(true));
		assertThat(order2.equals(order4),is(true));
		assertThat(order3.equals(order5),is(false));
		assertThat(order1.equals(order2),is(false));
		assertEquals(orderSet.size(),3);		
	}
}
