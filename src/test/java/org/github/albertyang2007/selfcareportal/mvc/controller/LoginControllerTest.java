package org.github.albertyang2007.selfcareportal.mvc.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.github.albertyang2007.selfcareportal.mvc.controller.LoginController;
import org.github.albertyang2007.selfcareportal.mvc.service.ILoginService;
import org.github.albertyang2007.selfcareportal.mvc.service.ILoginService.LoginResult;
import org.junit.After;
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
public class LoginControllerTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	public AnnotationMethodHandlerAdapter handlerAdapter;
	@Autowired
	private LoginController mockLoginController;
	@Autowired
	private ILoginService mockLoginService;

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	private String loginSeccView = "";
	private String loginView = "";

	private String userName = "";
	private String password = "";
	private LoginResult loginResult;

	@Before
	public void before() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		request.setRequestURI("/processLogin.do");
		request.setMethod(HttpMethod.POST.name());

		response = new MockHttpServletResponse();

		loginSeccView = (String) ReflectionTestUtils.getField(
				mockLoginController, "SUCC_VIEW");
		loginView = (String) ReflectionTestUtils.getField(mockLoginController,
				"LOGIN_VIEW");

		mockLoginService = EasyMock.createMock(ILoginService.class);

	}

	@After
	public void after() {
	}

	@Test
	public void testLoginOK() {
		userName = "eyaweiw";
		password = "123456";
		loginResult = LoginResult.successfully;
		request.addParameter("username", userName);
		request.addParameter("password", password);
		ModelAndView mv = null;
		String umVerify = null;

		EasyMock.expect(mockLoginService.login(userName, password)).andReturn(
				loginResult);

		// replay
		EasyMock.replay(mockLoginService);

		// inject mockPurchaseService to purchaseController
		ReflectionTestUtils.setField(mockLoginController, "loginService",
				mockLoginService, ILoginService.class);

		try {
			mv = handlerAdapter.handle(request, response, mockLoginController);
			umVerify = (String) request.getSession().getAttribute(
					"currentUserName");
		} catch (Exception e) {
			fail("Exception raised during testing! The exception is: "
					+ e.toString());
		}

		assertThat(mv, notNullValue());
		assertThat(response.getStatus(), is(200));
		assertThat(mv.getViewName(), is(loginSeccView));
		assertThat(umVerify, is(userName));		
		EasyMock.verify(mockLoginService);
	}

	@Test
	public void testLoginWrongPwd() {
		userName = "eyaweiw";
		password = "123456346456";
		loginResult = LoginResult.wrongPassword;
		request.addParameter("username", userName);
		request.addParameter("password", password);
		ModelAndView mv = null;

		EasyMock.expect(mockLoginService.login(userName, password)).andReturn(
				loginResult);

		// replay
		EasyMock.replay(mockLoginService);

		// inject mockPurchaseService to purchaseController
		ReflectionTestUtils.setField(mockLoginController, "loginService",
				mockLoginService, ILoginService.class);

		try {
			mv = handlerAdapter.handle(request, response, mockLoginController);
		} catch (Exception e) {
			fail("Exception raised during testing! The exception is: "
					+ e.toString());
		}

		assertThat(mv, notNullValue());
		assertThat(response.getStatus(), is(200));
		assertThat((String)mv.getModelMap().get("currentUserName"),is(nullValue()));
		assertThat(mv.getViewName(), is(loginView));
		EasyMock.verify(mockLoginService);
	}

	@Test
	public void testLoginUnknownUser() {
		userName = "noSuchUser";
		password = "123456346456";
		loginResult = LoginResult.noSuchUser;
		request.addParameter("username", userName);
		request.addParameter("password", password);
		ModelAndView mv = null;

		EasyMock.expect(mockLoginService.login(userName, password)).andReturn(
				loginResult);

		// replay
		EasyMock.replay(mockLoginService);

		// inject mockPurchaseService to purchaseController
		ReflectionTestUtils.setField(mockLoginController, "loginService",
				mockLoginService, ILoginService.class);

		try {
			mv = handlerAdapter.handle(request, response, mockLoginController);
		} catch (Exception e) {
			fail("Exception raised during testing! The exception is: "
					+ e.toString());
		}

		assertThat(mv, notNullValue());
		assertThat(response.getStatus(), is(200));
		assertThat((String)mv.getModelMap().get("currentUserName"),is(nullValue()));
		assertThat(mv.getViewName(), is(loginView));
		EasyMock.verify(mockLoginService);
	}

	@Test
	public void testLoginBlankInput() {
		request.addParameter("username", "");
		request.addParameter("password", "");
		ModelAndView mv = null;
		try {
			mv = handlerAdapter.handle(request, response, mockLoginController);
		} catch (Exception e) {
			fail("Exception raised during testing! The exception is: "
					+ e.toString());
		}

		assertThat(mv, notNullValue());
		assertThat(response.getStatus(), is(200));
		assertThat((String)mv.getModelMap().get("currentUserName"),is(nullValue()));
		assertThat(mv.getViewName(), is(loginView));
	}
}
