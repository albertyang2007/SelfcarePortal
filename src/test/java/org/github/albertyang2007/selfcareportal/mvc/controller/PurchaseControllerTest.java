package org.github.albertyang2007.selfcareportal.mvc.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.github.albertyang2007.selfcareportal.mvc.controller.PurchaseController;
import org.github.albertyang2007.selfcareportal.mvc.service.IPurchaseService;
import org.github.albertyang2007.selfcareportal.repository.entity.MyOrder;
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
@ContextConfiguration(locations = {"classpath:selfcarePortalDispatcher-servlet.xml"})
public class PurchaseControllerTest extends
				AbstractTransactionalJUnit4SpringContextTests {
	@Autowired	
	private AnnotationMethodHandlerAdapter handlerAdapter;	
	
	@Autowired
	private PurchaseController purchaseController;
	
	// Mock the Request, Resposne and Session
	private MockHttpServletRequest mockRequest = null;
	private MockHttpServletResponse mockResponse = null;
	private MockHttpSession mockSession = null;
	
	// Hold the purchase success & failure view name
	private static String purchaseSuccView = null;
	private static String purchaseFailView = null;
	
	// Session attribute and its value
	private static String currentUserNameAttribute = "currentUserName"; 
	private static String currentUserName = "Erica";
	
	// Request parameter name and its value
	private static String requestParmName = "productID";	
	private static String[] hardCodeProductIDs = new String[] {"123456", "123457", "123458"};
		
	// Hard code order ID
	private static final int hardCodeOrderID = 999;

	@Before
	public void before() {
		mockRequest = new MockHttpServletRequest("POST", "/purchase.do");
		mockResponse = new MockHttpServletResponse();
		mockSession = new MockHttpSession();
		
		mockSession.setAttribute(currentUserNameAttribute, currentUserName);
		mockRequest.setSession(mockSession);
		
		// get purchase success and failure view name
		purchaseSuccView = (String) ReflectionTestUtils.getField(purchaseController, "SUCCESS_VIEW");
	    purchaseFailView = (String) ReflectionTestUtils.getField(purchaseController, "FAIL_VIEW");
	}


	@Test
	public void testProcessPurchaseOrderSuccess() {  		
		MyOrder myOrder = new MyOrder();
		myOrder.setOrderId(hardCodeOrderID); 
		
		mockRequest.setParameter(requestParmName, hardCodeProductIDs);
		
		// mock the Purchase Service
		IPurchaseService mockPurchaseService = EasyMock.createMock(IPurchaseService.class);
		EasyMock.expect(mockPurchaseService.makeOrder(currentUserName, hardCodeProductIDs))
			.andReturn(myOrder);

		// replay
		EasyMock.replay(mockPurchaseService);
		
		// inject mockPurchaseService to purchaseController
		ReflectionTestUtils.setField(purchaseController, "purchaseService", 
				mockPurchaseService, IPurchaseService.class);
		
		ModelAndView mv = null;		 
		try {					
			mv = handlerAdapter.handle(mockRequest, mockResponse, purchaseController);
		} catch (Exception e) {			
			fail("Exception raised during testing! The exception is: " + e.toString());			
		}
			
		assertThat(mv, notNullValue());
		assertThat(mockResponse.getStatus(), is(200));
		assertThat(mv.getViewName(), is(purchaseSuccView));	
		assertThat(mv.getModelMap().get("myorderlist"), notNullValue());
		assertThat(mv.getModelMap().get("requestSource"), notNullValue());
		EasyMock.verify(mockPurchaseService);
	}
	
	@Test
	public void testProcessPurchaseOrderFailureWithEmptyOrder() {  							
		ModelAndView mv = null;
		 
		try {									
			mv = handlerAdapter.handle(mockRequest, mockResponse, purchaseController);						
		} catch (Exception e) {			
			fail("Exception raised during testing! The exception is: " + e.toString());
		}
			
		assertThat(mv, notNullValue());
		assertThat(mockResponse.getStatus(), is(200));
		assertThat(mv.getViewName(), is(purchaseFailView));		
	}
	
	@Test
	public void testProcessPurchaseOrderFailureByReturnEmptyOrderID() {  		
		mockRequest.setParameter(requestParmName, hardCodeProductIDs);
		
		// mock the Purchase Service
		IPurchaseService mockPurchaseService = EasyMock.createMock(IPurchaseService.class);
		EasyMock.expect(mockPurchaseService.makeOrder(currentUserName, hardCodeProductIDs))
			.andReturn(null);

		// replay
		EasyMock.replay(mockPurchaseService);
		
		// inject mockPurchaseService to purchaseController
		ReflectionTestUtils.setField(purchaseController, "purchaseService", 
				mockPurchaseService, IPurchaseService.class);
						
		ModelAndView mv = null;
		 
		try {					
			mv = handlerAdapter.handle(mockRequest, mockResponse, purchaseController);
		} catch (Exception e) {			
			fail("Exception raised during testing! The exception is: " + e.toString());			
		}
			
		assertThat(mv, notNullValue());
		assertThat(mockResponse.getStatus(), is(200));
		assertThat(mv.getViewName(), is(purchaseFailView));	
		EasyMock.verify(mockPurchaseService);
	}
}
