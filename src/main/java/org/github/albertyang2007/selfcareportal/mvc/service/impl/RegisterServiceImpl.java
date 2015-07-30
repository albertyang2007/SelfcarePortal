package org.github.albertyang2007.selfcareportal.mvc.service.impl;

import org.github.albertyang2007.selfcareportal.mvc.service.IRegisterService;
import org.github.albertyang2007.selfcareportal.repository.entity.User;
import org.github.albertyang2007.selfcareportal.repository.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements IRegisterService {

	@Autowired
	private DataAccessService service;

	@Override
	public RegisterResult register(String userId, String nickName,
			String password) {
		// TODO Auto-generated method stub

		User user = this.service.findUser(userId);

		if (user != null) {
			return RegisterResult.userAlreadyExist;

		} else {
			user = this.service.addUser(userId, password, nickName);

			return (user != null) ? RegisterResult.successfully
					: RegisterResult.unknownError;

		}
	}

}
