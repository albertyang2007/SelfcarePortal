package org.github.albertyang2007.selfcareportal.mvc.service.impl;

import org.github.albertyang2007.selfcareportal.mvc.service.ILoginService;
import org.github.albertyang2007.selfcareportal.repository.entity.User;
import org.github.albertyang2007.selfcareportal.repository.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {
	@Autowired
	private DataAccessService service;

	public LoginResult login(String name, String password) {
		// Invoke DAO service to verify the user name and password

		User user = this.service.findUser(name);
		if (user == null) {
			return LoginResult.noSuchUser;
		} else {
			if (user.getPassword().equals(password)) {
				return LoginResult.successfully;
			} else {
				return LoginResult.wrongPassword;
			}
		}
	}

}
