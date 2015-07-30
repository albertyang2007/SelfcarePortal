package org.github.albertyang2007.selfcareportal.mvc.service;


public interface IRegisterService {
	public enum RegisterResult {
		successfully, userAlreadyExist, unknownError
	};
	RegisterResult register(String userId, String nickName, String password);
}
