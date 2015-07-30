package org.github.albertyang2007.selfcareportal.mvc.controller;

import org.github.albertyang2007.selfcareportal.mvc.service.IRegisterService;
import org.github.albertyang2007.selfcareportal.mvc.service.IRegisterService.RegisterResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class RegisterController {
	@Autowired
	private IRegisterService registerService;

	private static final String REGISTER_VIEW = "register";

	private static final String RESULT_ATTRIBUTE = "resultInfo";

	@RequestMapping(value = "/register.do", method = RequestMethod.GET)
	public ModelAndView processRegister(ModelMap model) {
		model.addAttribute(RESULT_ATTRIBUTE, "New Register");
		return new ModelAndView(REGISTER_VIEW);
	}

	@RequestMapping(value = "/processRegister.do", method = RequestMethod.POST)
	public ModelAndView processNewRegister(
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "nickName", required = false) String nickName,
			@RequestParam(value = "password", required = false) String password,
			ModelMap model) {
		if (!this.validate(userId, nickName, password)) {
			model.addAttribute(RESULT_ATTRIBUTE,
					"Input user Name or password is incorrect.");
			return new ModelAndView(REGISTER_VIEW);
		}

		RegisterResult registerResult = this.registerService.register(userId,
				nickName, password);

		if (registerResult == RegisterResult.successfully) {
			model.addAttribute(RESULT_ATTRIBUTE, userId
					+ " register successfully!");
		} else if (registerResult == RegisterResult.userAlreadyExist) {
			model.addAttribute(RESULT_ATTRIBUTE, "UserId '" + userId
					+ "' already exist.");
		} else {
			model.addAttribute(RESULT_ATTRIBUTE,
					"Register fails. Unknown reason.");
		}

		return new ModelAndView(REGISTER_VIEW);
	}

	public boolean validate(String userId, String nickName, String password) {
		if (userId == null || nickName == null || password == null) {
			return true;
		}

		if (userId.equals("") || nickName.equals("") || password.equals("")) {
			return false;
		}
		return true;
	}

}
