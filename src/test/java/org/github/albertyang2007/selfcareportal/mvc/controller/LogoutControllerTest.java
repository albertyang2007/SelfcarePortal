package org.github.albertyang2007.selfcareportal.mvc.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.github.albertyang2007.selfcareportal.mvc.controller.LogoutController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:selfcarePortalDispatcher-servlet.xml" })
public class LogoutControllerTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired	
	private AnnotationMethodHandlerAdapter handlerAdapter;
	
	@Autowired
	private LogoutController logoutController;
		
	// Mock the request & response
	private MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/logout.do");;
	private MockHttpServletResponse mockResponse = new MockHttpServletResponse();;
		
	@Test
	public void testLogoutSuccess() {  
		// get purchase success and failure view name
		String logoutSeccView = (String) ReflectionTestUtils.getField(logoutController, "VIEW");
		
		ModelAndView mv = null;		 
		try {								
			mv = handlerAdapter.handle(mockRequest, mockResponse, logoutController);						
		} catch (Exception e) {			
			fail("Exception raised during testing! The exception is: " + e.toString());
		}
			
		assertThat(mv, notNullValue());
		assertThat(mockResponse.getStatus(), is(200));
		assertThat(mv.getViewName(), is(logoutSeccView));		
	}
}
