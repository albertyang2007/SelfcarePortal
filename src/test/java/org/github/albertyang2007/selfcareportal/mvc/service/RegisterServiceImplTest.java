package org.github.albertyang2007.selfcareportal.mvc.service;

import org.easymock.EasyMock;
import org.github.albertyang2007.selfcareportal.mvc.service.IRegisterService.RegisterResult;
import org.github.albertyang2007.selfcareportal.mvc.service.impl.RegisterServiceImpl;
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
public class RegisterServiceImplTest {

	@Autowired
	private AnnotationMethodHandlerAdapter handlerAdapter;

	@Autowired
	private RegisterServiceImpl mockRegisterService;

	@Autowired
	private DataAccessService mockDAOService;
	private User user;
	private String userId;
	private String password;
	private String nickName;

	@Before
	public void before() {
		mockDAOService = EasyMock.createMock(DataAccessService.class);
		user = new User();
	}

	@After
	public void after() {
	}

	@Test
	public void testRegister() {
		userId = "eyaweiw";
		password = "123456";
		nickName = "Albert Yang";

		user.setUserId(userId);
		user.setPassword(password);
		user.setNickName(nickName);

		EasyMock.expect(mockDAOService.findUser(userId)).andReturn(null);
		EasyMock.expect(mockDAOService.addUser(userId, password, nickName))
				.andReturn(user);

		// replay
		EasyMock.replay(mockDAOService);

		// inject mockService
		ReflectionTestUtils.setField(mockRegisterService, "service",
				mockDAOService, DataAccessService.class);

		RegisterResult rtn = mockRegisterService.register(userId, nickName,
				password);
		Assert.assertEquals(rtn, RegisterResult.successfully);
		EasyMock.verify(mockDAOService);
	}

	@Test
	public void testRegisterUserAlreadyExist() {
		userId = "eyaweiw";
		password = "123456";
		nickName = "Albert Yang";

		user.setUserId(userId);
		user.setPassword(password);
		user.setNickName(nickName);

		EasyMock.expect(mockDAOService.findUser(userId)).andReturn(user);

		// replay
		EasyMock.replay(mockDAOService);

		// inject mockService
		ReflectionTestUtils.setField(mockRegisterService, "service",
				mockDAOService, DataAccessService.class);

		RegisterResult rtn = mockRegisterService.register(userId, nickName,
				password);
		Assert.assertEquals(rtn, RegisterResult.userAlreadyExist);
		EasyMock.verify(mockDAOService);
	}

	@Test
	public void testRegisterUnknownFails() {
		userId = "eyaweiw";
		password = "123456";
		nickName = "Albert Yang";

		user.setUserId(userId);
		user.setPassword(password);
		user.setNickName(nickName);

		EasyMock.expect(mockDAOService.findUser(userId)).andReturn(null);
		EasyMock.expect(mockDAOService.addUser(userId, password, nickName))
				.andReturn(null);

		// replay
		EasyMock.replay(mockDAOService);

		// inject mockService
		ReflectionTestUtils.setField(mockRegisterService, "service",
				mockDAOService, DataAccessService.class);

		RegisterResult rtn = mockRegisterService.register(userId, nickName,
				password);
		Assert.assertEquals(rtn, RegisterResult.unknownError);
		EasyMock.verify(mockDAOService);
	}
}