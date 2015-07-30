package org.github.albertyang2007.selfcareportal.mvc.service;

public interface ILoginService {
	public enum LoginResult {
		successfully, noSuchUser, wrongPassword, unknownError
	};

	LoginResult login(String name, String password);
}
