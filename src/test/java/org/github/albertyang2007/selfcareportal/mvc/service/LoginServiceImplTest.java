package org.github.albertyang2007.selfcareportal.mvc.service;

import org.easymock.EasyMock;
import org.github.albertyang2007.selfcareportal.mvc.service.ILoginService.LoginResult;
import org.github.albertyang2007.selfcareportal.mvc.service.impl.LoginServiceImpl;
import org.github.albertyang2007.selfcareportal.repository.entity.User;
import org.github.albertyang2007.selfcareportal.repository.service.DataAccessService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:selfcarePortalDispatcher-servlet.xml" })
public class LoginServiceImplTest {

	@Autowired
	private AnnotationMethodHandlerAdapter handlerAdapter;

	@Autowired
	private LoginServiceImpl mockLoginService;

	@Autowired
	private DataAccessService mockDAOService;
	private User user;
	private String userName;
	private String password;

	@Before
	public void before() {
		mockDAOService = EasyMock.createMock(DataAccessService.class);
		user = new User();
	}

	@After
	public void after() {
	}

	@Test
	public void testLogin() {
		userName = "eyaweiw";
		password = "123456";

		user.setUserId(userName);
		user.setPassword(password);

		EasyMock.expect(mockDAOService.findUser(userName)).andReturn(user);

		// replay
		EasyMock.replay(mockDAOService);

		// inject mockService
		ReflectionTestUtils.setField(mockLoginService, "service",
				mockDAOService, DataAccessService.class);

		LoginResult rtn = mockLoginService.login(userName, password);
		Assert.assertEquals(rtn, LoginResult.successfully);
		EasyMock.verify(mockDAOService);
	}

	@Test
	public void testLoginWrongPwd() {
		userName = "eyaweiw";
		password = "123456";
		
		String wrongPwd = "wrongPwd";

		user.setUserId(userName);
		user.setPassword(wrongPwd);

		EasyMock.expect(mockDAOService.findUser(userName)).andReturn(user);

		// replay
		EasyMock.replay(mockDAOService);

		// inject mockService
		ReflectionTestUtils.setField(mockLoginService, "service",
				mockDAOService, DataAccessService.class);

		LoginResult rtn = mockLoginService.login(userName, password);
		Assert.assertEquals(rtn, LoginResult.wrongPassword);
		EasyMock.verify(mockDAOService);
	}

	@Test
	public void testLoginNoSuchUser() {
		userName = "noSuchUser";
		password = "123456";

		user.setUserId(userName);
		user.setPassword(password);

		EasyMock.expect(mockDAOService.findUser(userName)).andReturn(null);

		// replay
		EasyMock.replay(mockDAOService);

		// inject mockService
		ReflectionTestUtils.setField(mockLoginService, "service",
				mockDAOService, DataAccessService.class);

		LoginResult rtn = mockLoginService.login(userName, password);
		Assert.assertEquals(rtn, LoginResult.noSuchUser);
		EasyMock.verify(mockDAOService);
	}
}
