package org.github.albertyang2007.selfcareportal.mvc.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.github.albertyang2007.selfcareportal.mvc.controller.MyProductController;
import org.github.albertyang2007.selfcareportal.mvc.service.IMyProductService;
import org.github.albertyang2007.selfcareportal.repository.entity.Item;
import org.github.albertyang2007.selfcareportal.repository.entity.MyOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:selfcarePortalDispatcher-servlet.xml" })
public class MyProductControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired	
	private AnnotationMethodHandlerAdapter handlerAdapter;
	
	private MockHttpSession currentsession = null;
	
	private MockHttpServletRequest request = null;
	
	private MockHttpServletResponse response = null;
	
	private static String currentUserNameAttr = "currentUserName"; 
	private static String currentUserName = "order_user";
	
	private static List<MyOrder> expectedproduct = null;
	private static int expectpagenum = 0;
	
	@Autowired
	private MyProductController productctrl;

	@Before
	public void before() {
		request = new MockHttpServletRequest("POST","/myproductlist.do");
		response = new MockHttpServletResponse();
		currentsession = new MockHttpSession();
		
		currentsession.setAttribute(currentUserNameAttr, currentUserName);
		request.setSession(currentsession);
		
		expectpagenum = 1;
		
		expectedproduct = builddata();

	}
	
   
	@Test
	public void testGetProductListSucc(){
		
		request.setParameter("page", "1");
		
		IMyProductService mockMyProductService = EasyMock.createMock(IMyProductService.class);
		EasyMock.expect(mockMyProductService.getpurchaselist(currentUserName, 1, 5))
			.andReturn(expectedproduct);
		EasyMock.expect(mockMyProductService.getpagenum(currentUserName, 5)).andReturn(expectpagenum);
		
		// replay
		EasyMock.replay(mockMyProductService);
		
		// inject the @autowired field mockMyProductService to MyProductController
		ReflectionTestUtils.setField(productctrl, "myproductservice", mockMyProductService, IMyProductService.class);
						
		ModelAndView mv = null;
		
		try{
			mv = handlerAdapter.handle(request, response, productctrl);
		}catch(Exception e){
		  e.printStackTrace();
		}
		
		assertThat(mv, notNullValue());
		assertThat(response.getStatus(), is(200));
		assertThat(mv.getViewName(), equalTo("myproductlist"));
		assertThat(currentsession.getAttribute("currentpage"),notNullValue());
		assertThat(mv.getModelMap().get("myorderlist"),notNullValue());
		assertThat(mv.getModelMap().get("totalpage"),notNullValue());
		EasyMock.verify(mockMyProductService);
		
	}


	@Test
	public void testGetProductListwopage(){
		
		
		IMyProductService mockMyProductService = EasyMock.createMock(IMyProductService.class);
		EasyMock.expect(mockMyProductService.getpurchaselist(currentUserName, 1, 5))
			.andReturn(expectedproduct);
		EasyMock.expect(mockMyProductService.getpagenum(currentUserName, 5)).andReturn(expectpagenum);
		
		// replay
		EasyMock.replay(mockMyProductService);
		
		// inject the @autowired field mockMyProductService to MyProductController
		ReflectionTestUtils.setField(productctrl, "myproductservice", mockMyProductService, IMyProductService.class);
						
		ModelAndView mv = null;
		
		try{
			mv = handlerAdapter.handle(request, response, productctrl);
		}catch(Exception e){
		  e.printStackTrace();
		}
		
		assertThat(mv, notNullValue());
		assertThat(response.getStatus(), is(200));
		assertThat(mv.getViewName(), equalTo("myproductlist"));
		assertThat(currentsession.getAttribute("currentpage"),notNullValue());
		EasyMock.verify(mockMyProductService);	
	}
	
	
	@After
	public void after(){
		currentsession.removeAttribute("currentUserName");
	}
	
   private List<MyOrder> builddata(){
        
		List<MyOrder> myorderlist = new ArrayList<MyOrder>();
		
		MyOrder oneoder = new MyOrder();
		
		List<Item> productlist = new ArrayList<Item>();
		
		Item oneproduct = new Item();
	
		oneproduct.setDescription("iPhone5s");
		oneproduct.setPrice(5888.00);
		oneproduct.setProductId(1);
		oneproduct.setProductNo("1001");
		productlist.add(oneproduct);
		
		Item twoproduct = new Item();
		twoproduct.setDescription("iPhone4s");
		twoproduct.setPrice(4888.00);
		twoproduct.setProductId(2);
		twoproduct.setProductNo("1002");
		productlist.add(twoproduct);
		
		oneoder.setCreateDate(new Date());
		oneoder.setOrderId(10001);
		oneoder.setProducts(productlist);
		
		myorderlist.add(oneoder);
		
		return myorderlist;
	}
}
