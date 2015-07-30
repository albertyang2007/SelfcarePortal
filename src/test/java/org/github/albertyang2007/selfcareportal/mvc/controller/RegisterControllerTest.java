package org.github.albertyang2007.selfcareportal.mvc.controller;

import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.github.albertyang2007.selfcareportal.mvc.controller.RegisterController;
import org.github.albertyang2007.selfcareportal.mvc.service.IRegisterService;
import org.github.albertyang2007.selfcareportal.mvc.service.IRegisterService.RegisterResult;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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
public class RegisterControllerTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	public AnnotationMethodHandlerAdapter handlerAdapter;
	@Autowired
	private RegisterController mockRegisterController;

	@Autowired
	private IRegisterService mockRegisterService;

	private static MockHttpServletRequest request;

	private static MockHttpServletResponse response;

	private String registerView;

	private String userId = "";
	private String nickName = "";
	private String password = "";

	@Before
	public void before() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		request.setRequestURI("/processRegister.do");
		request.setMethod(HttpMethod.POST.name());

		response = new MockHttpServletResponse();

		registerView = (String) ReflectionTestUtils.getField(
				mockRegisterController, "REGISTER_VIEW");

		mockRegisterService = EasyMock.createMock(IRegisterService.class);
	}

	@After
	public void after() {
	}

	@Test
	public void testRegisterOK() {
		userId = "eyaweiw";
		nickName = "Albert Yang";
		password = "123456";
		request.addParameter("userId", userId);
		request.addParameter("password", password);
		request.addParameter("nickName", nickName);
		ModelAndView mv = null;

		EasyMock.expect(
				mockRegisterService.register(userId, nickName, password))
				.andReturn(RegisterResult.successfully);

		// replay
		EasyMock.replay(mockRegisterService);

		// inject mockPurchaseService to purchaseController
		ReflectionTestUtils.setField(mockRegisterController, "registerService",
				mockRegisterService, IRegisterService.class);

		try {
			mv = handlerAdapter.handle(request, response,
					mockRegisterController);
		} catch (Exception e) {
			fail("Exception raised during testing! The exception is: " + e.toString());
		}

		Assert.assertNotNull(mv);
		Assert.assertEquals(response.getStatus(), 200);
		Assert.assertEquals(mv.getViewName(), this.registerView);
		EasyMock.verify(mockRegisterService);
	}

	@Test
	public void testRegisterUserAlreadyExist() {
		userId = "eyaweiw";
		nickName = "Albert Yang";
		password = "123456";
		request.addParameter("userId", userId);
		request.addParameter("password", password);
		request.addParameter("nickName", nickName);
		ModelAndView mv = null;

		EasyMock.expect(
				mockRegisterService.register(userId, nickName, password))
				.andReturn(RegisterResult.userAlreadyExist);

		// replay
		EasyMock.replay(mockRegisterService);

		// inject mockPurchaseService to purchaseController
		ReflectionTestUtils.setField(mockRegisterController, "registerService",
				mockRegisterService, IRegisterService.class);

		try {
			mv = handlerAdapter.handle(request, response,
					mockRegisterController);
		} catch (Exception e) {
			fail("Exception raised during testing! The exception is: " + e.toString());
		}

		Assert.assertNotNull(mv);
		Assert.assertEquals(response.getStatus(), 200);
		Assert.assertEquals(mv.getViewName(), this.registerView);
		EasyMock.verify(mockRegisterService);
	}

	@Test
	public void testRegisterBlankInput() {
		request.addParameter("userId", "");
		request.addParameter("password", "");
		request.addParameter("nickName", "");
		ModelAndView mv = null;
		try {
			mv = handlerAdapter.handle(request, response,
					mockRegisterController);
		} catch (Exception e) {
			fail("Exception raised during testing! The exception is: " + e.toString());
		}

		Assert.assertNotNull(mv);
		Assert.assertEquals(response.getStatus(), 200);
		Assert.assertEquals(mv.getViewName(), this.registerView);
	}
}
