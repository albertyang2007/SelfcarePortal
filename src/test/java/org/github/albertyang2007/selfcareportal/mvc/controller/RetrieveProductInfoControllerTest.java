package org.github.albertyang2007.selfcareportal.mvc.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.github.albertyang2007.selfcareportal.mvc.controller.RetrieveProductInfoController;
import org.github.albertyang2007.selfcareportal.mvc.service.IRetrieveProductInfoService;
import org.github.albertyang2007.selfcareportal.util.Product;
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
public class RetrieveProductInfoControllerTest extends
		AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	public AnnotationMethodHandlerAdapter handlerAdapter;
	
	@Autowired
	public RetrieveProductInfoController rpiController;
	
	@Autowired
	public IRetrieveProductInfoService mockRPIService;
	
	private MockHttpServletRequest request;

	private MockHttpServletResponse response;
	
	private static String retriveProductInfoView = null;
	
	private static Product[] productsOnPage1= new Product[]{new Product("1001","iphone4",4444.0,true),
		new Product("1002","iphone5",5555.0,true),
		new Product("1003","iphone6",6666.0,true)};
	
	private static Product[] productsOnPage2= new Product[]{new Product("1004","ipad",4444.0,true),
		new Product("1005","ipad2",5555.0,true),
		new Product("1006","ipad3",6666.0,true),
		new Product("1007","ipad4",6666.0,true)};
	
	private static Product[] productsOnPage3= new Product[]{new Product("1008","huawei",4444.0,true),
		new Product("1009","htc",5555.0,true),
		new Product("1010","sony",6666.0,true),
		new Product("1011","nokia",6666.0,true)};
	
	private MockHttpSession currentsession;
	
	private static int totalPage = 3;

	@Before
	public void before() {
		request = new MockHttpServletRequest("GET","/retrieveProductInfo.do");
		response = new MockHttpServletResponse();
		currentsession = new MockHttpSession();
		retriveProductInfoView = (String) ReflectionTestUtils.getField(rpiController,"VIEW");
		
	}
	
	@Test
	public void testRetrieveProductDefault(){
		
		request.setSession(currentsession);
				
		mockRPIService=EasyMock.createMock(IRetrieveProductInfoService.class);
		EasyMock.expect(mockRPIService.retrieveProductInfoPerPage(1)).andReturn(productsOnPage1);
		EasyMock.expect(mockRPIService.retrieveProductInfoPerPage(1)).andReturn(productsOnPage1);
		EasyMock.expect(mockRPIService.getTotalPage()).andReturn(totalPage);
		EasyMock.replay(mockRPIService);
		
		ReflectionTestUtils.setField(rpiController, "retrieveProductInfoService", 
				mockRPIService, IRetrieveProductInfoService.class);
		
		ModelAndView mv = null;	
		try {					
			mv = handlerAdapter.handle(request, response, rpiController);
		} catch (Exception e) {			
			fail("Exception raised during testing! The exception is: " + e.toString());			
		}
		
		assertThat(mv, notNullValue());
		assertThat(response.getStatus(), is(200));
		assertThat(mv.getViewName(), equalTo(retriveProductInfoView));	
		assertThat((Integer)currentsession.getAttribute("currentPage"),is(1));
		assertThat((Product[])mv.getModelMap().get("products"),equalTo(productsOnPage1));
		assertThat((Product[])mv.getModelMap().get("hotProducts"),equalTo(productsOnPage1));
		assertThat((Integer)mv.getModelMap().get("totalPage"),is(totalPage));
		EasyMock.verify(mockRPIService);
		
	}
	
	@Test
	public void testRetrieveProductWithSessionAttr(){
		
		currentsession.setAttribute("currentPage",2);
		request.setSession(currentsession);
				
		mockRPIService=EasyMock.createMock(IRetrieveProductInfoService.class);
		EasyMock.expect(mockRPIService.retrieveProductInfoPerPage(1)).andReturn(productsOnPage1);
		EasyMock.expect(mockRPIService.retrieveProductInfoPerPage(2)).andReturn(productsOnPage2);
		EasyMock.expect(mockRPIService.getTotalPage()).andReturn(totalPage);
		EasyMock.replay(mockRPIService);
		
		ReflectionTestUtils.setField(rpiController, "retrieveProductInfoService", 
				mockRPIService, IRetrieveProductInfoService.class);
		
		ModelAndView mv = null;	
		try {					
			mv = handlerAdapter.handle(request, response, rpiController);
		} catch (Exception e) {			
			fail("Exception raised during testing! The exception is: " + e.toString());			
		}
		
		assertThat(mv, notNullValue());
		assertThat(response.getStatus(), is(200));
		assertThat(mv.getViewName(), equalTo("retrieveProduct"));	
		assertThat((Integer)currentsession.getAttribute("currentPage"),is(2));
		assertThat((Product[])mv.getModelMap().get("products"),equalTo(productsOnPage2));
		assertThat((Product[])mv.getModelMap().get("hotProducts"),equalTo(productsOnPage1));
		assertThat((Integer)mv.getModelMap().get("totalPage"),is(totalPage));
		EasyMock.verify(mockRPIService);
		
	}
	
	@Test
	public void testRetrieveProductWithSessionAttrAndParm(){
		
		currentsession.setAttribute("currentPage",2);
		request.setSession(currentsession);
		
		request.setParameter("page","3");
				
		mockRPIService=EasyMock.createMock(IRetrieveProductInfoService.class);
		EasyMock.expect(mockRPIService.retrieveProductInfoPerPage(1)).andReturn(productsOnPage1);
		EasyMock.expect(mockRPIService.retrieveProductInfoPerPage(3)).andReturn(productsOnPage3);
		EasyMock.expect(mockRPIService.getTotalPage()).andReturn(totalPage);
		EasyMock.replay(mockRPIService);
		
		ReflectionTestUtils.setField(rpiController, "retrieveProductInfoService", 
				mockRPIService, IRetrieveProductInfoService.class);
		
		ModelAndView mv = null;	
		try {					
			mv = handlerAdapter.handle(request, response, rpiController);
		} catch (Exception e) {			
			fail("Exception raised during testing! The exception is: " + e.toString());			
		}
		
		assertThat(mv, notNullValue());
		assertThat(response.getStatus(), is(200));
		assertThat(mv.getViewName(), equalTo("retrieveProduct"));	
		assertThat((Integer)currentsession.getAttribute("currentPage"),is(3));
		assertThat((Product[])mv.getModelMap().get("products"),equalTo(productsOnPage3));
		assertThat((Product[])mv.getModelMap().get("hotProducts"),equalTo(productsOnPage1));
		assertThat((Integer)mv.getModelMap().get("totalPage"),is(totalPage));
		EasyMock.verify(mockRPIService);
		
	}
}
