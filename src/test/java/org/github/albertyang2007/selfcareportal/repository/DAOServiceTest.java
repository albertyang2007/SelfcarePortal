package org.github.albertyang2007.selfcareportal.repository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.github.albertyang2007.selfcareportal.repository.entity.*;
import org.github.albertyang2007.selfcareportal.repository.service.DataAccessService;
import org.github.albertyang2007.selfcareportal.util.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DAOServiceTest {
	
	@Autowired
	private DataAccessService service;

	@Test
	public void testAddUserSuccessful(){
		String userId = "not_exist_user";
		String password = "123456";
		String nickName = "Connie Xia";
		User user = service.addUser(userId, password, nickName);
		assertThat(user,notNullValue());
		user = service.findUser(userId);
		assertThat(user,notNullValue());
		assertEquals(userId,user.getUserId());
		assertEquals(password,user.getPassword());
		assertEquals(nickName,user.getNickName());		
	}
	
	@Test
	public void testAddUserAlreadyExists(){
		String userId = "existing_user";
		String password = "123456";
		String nickName = "Kay Zhou";		
		User user = service.addUser(userId, password, nickName);
		assertThat(user,nullValue());	
	}
	
	@Test
	public void testFindUserSuccessful(){
		String userId = "existing_user";
		User user = service.findUser(userId);
		assertThat(user,notNullValue());	
		assertEquals(userId,user.getUserId());		
	}
	
	@Test
	public void testFindUserNotExist(){
		String userId = "not_exist_user";
		User user = service.findUser(userId);
		assertThat(user,nullValue());		
	}
	
	@Test
	public void testAddOrder(){
		ArrayList<Product> products = new ArrayList<Product>();
		String userId = "order_user";
		products.add(new Product("2563","iPhone5",5674.0,true));
		products.add(new Product("1125","iPad",674.0,true));
		products.add(new Product("1125","iPad",674.0,true));
		MyOrder result= service.addOrder(userId,products);
		assertThat(result,notNullValue());
		assertEquals(userId,result.getUser().getUserId());
		assertEquals(3,result.getProducts().size());
		for(Item hp:result.getProducts()){
			assertThat(hp.getProductId(),notNullValue());
		}
	}
	
	@Test
	public void testAddOrder_userNotExist(){
		ArrayList<Product> products = new ArrayList<Product>();
		String userId = "not_exist_user";
		products.add(new Product("1526","iPhone5",5674.0,false));
		products.add(new Product("3365","iPad",674.0,true));
		MyOrder result= service.addOrder(userId,products);
		assertThat(result,nullValue());

	}
	
	@Test
	public void testFindOrder(){
		String userId = "order_user";
		List<MyOrder> orders = service.findOrders(userId);
		assertThat(orders,notNullValue());
		assertEquals(1,orders.size());
		for(MyOrder o:orders){
			assertEquals(userId,o.getUser().getUserId());
		}
	}
	
	@Test
	public void testFindOrder_userNotExist(){
		String userId = "not_exist_user";
		List<MyOrder> orders = service.findOrders(userId);
		assertThat(orders,nullValue());
	}
	
	@Test
	public void testFindOrder_noOrderExist(){
		String userId = "no_order_user";
		List<MyOrder> orders = service.findOrders(userId);
		assertThat(orders,nullValue());
	}
	
	@Test
	public void testfindOrdersbypage(){
		String userId = "order_user";
		int start = 0;
	    int ordernum = 5;
	    AddMoreOrder();
		List<MyOrder> orders = service.findOrdersbypage(userId,start,ordernum);
		assertThat(orders,notNullValue());
		assertThat(orders.size(),greaterThan(1));
		for(MyOrder o:orders){
			assertThat(userId,equalTo(o.getUser().getUserId()));
		}
	}
	
	@Test
	public void testfindOrdersbypage_userNotExist(){
		String userId = "exyzxyz";
		int start = 2;
	    int ordernum = 5;
		List<MyOrder> orders = service.findOrdersbypage(userId,start,ordernum);
		assertThat(orders,nullValue());
	}
	
	@Test
	public void testfindOrdersbypage_noOrderExist(){
		String userId = "no_order_user";
		int start = 2;
	    int ordernum = 5;
		List<MyOrder> orders = service.findOrdersbypage(userId,start,ordernum);
		assertThat(orders,nullValue());
	}
	
	@Test
	public void testfindOrdersNum(){
		String userId = "order_user";
		AddMoreOrder();
		long ordernum = service.findOrdersNum(userId);
		assertThat(ordernum,greaterThan(1l));
	}
	
	@Test
	public void testfindOrdersNum_userNotExist(){
		String userId = "exyzxyz";
		long ordernum = service.findOrdersNum(userId);
		assertThat(ordernum,is(0l));
	}
	
	@Test
	public void testfindOrdersNum_noOrderExist(){
		String userId = "no_order_user";
		long  ordernum = service.findOrdersNum(userId);
		assertThat(ordernum,is(0l));
	}
	
	@Before
	public void initDB() {
		service.addUser("existing_user", "123456", "Kay Zhou");
		service.addUser("order_user", "123456", "Ten Tan");
		service.addUser("no_order_user", "123456", "Albert Yang");
		ArrayList<Product> products = new ArrayList<Product>();
		products.add(new Product("1548","iPhone6",8674.0,false));
		products.add(new Product("3364","iPad5",2674.0,true));
		service.addOrder("order_user",products);		
	}

	@After
	public void dropDB() {
		List<MyOrder> orders = service.findOrders("order_user");
		if(orders!=null){
			for(MyOrder order:orders){
				service.delOrderById(order.getOrderId());
			}
		}
		service.delUser("existing_user");
		service.delUser("not_exist_user");
		service.delUser("order_user");
		service.delUser("no_order_user");
	}
	
	public void AddMoreOrder(){
		ArrayList<Product> products1 = new ArrayList<Product>();
		String userId = "order_user";
		products1.add(new Product("1234","abcd",5674.0,true));
		products1.add(new Product("1235","abec",674.0,true));
		products1.add(new Product("1236","defd",674.0,true));
	    service.addOrder(userId,products1);
	    ArrayList<Product> products2 = new ArrayList<Product>();
	    products2.add(new Product("1237","kkkk",5674.0,true));
	    products2.add(new Product("1238","bbbb",674.0,true));
	    products2.add(new Product("1239","dddd",674.0,true));
	    service.addOrder(userId,products2);
	}
}
