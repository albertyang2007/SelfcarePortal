package org.github.albertyang2007.selfcareportal.mvc.controller;

import org.github.albertyang2007.selfcareportal.mvc.service.ILoginService;
import org.github.albertyang2007.selfcareportal.mvc.service.ILoginService.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("currentUserName")
public class LoginController {
	@Autowired
	private ILoginService loginService;

	private static final String SUCC_VIEW = "forward:retrieveProductInfo.do";
	private static final String LOGIN_VIEW = "login";

	private static final String RESULT_ATTRIBUTE = "resultInfo";

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public ModelAndView login(ModelMap model) {
		model.addAttribute(RESULT_ATTRIBUTE, "Signin");
		return new ModelAndView(LOGIN_VIEW);
	}

	@RequestMapping(value = "/processLogin.do", method = RequestMethod.POST)
	public ModelAndView processLogin(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			ModelMap model) {

		if (!this.validate(username, password)) {
			model.addAttribute(RESULT_ATTRIBUTE,
					"Input user Name or password is incorrect.");
			return new ModelAndView(LOGIN_VIEW);
		}

		LoginResult loginResult = this.loginService.login(username, password);

		if (loginResult == LoginResult.successfully) {
			model.addAttribute("currentUserName", username);

			return new ModelAndView(SUCC_VIEW);
		} else {
			if (loginResult == LoginResult.noSuchUser) {
				model.addAttribute(RESULT_ATTRIBUTE,
						"Login fails, no such user.");
			} else if (loginResult == LoginResult.wrongPassword) {
				model.addAttribute(RESULT_ATTRIBUTE,
						"Login fails, password incorrect.");
			}

			return new ModelAndView(LOGIN_VIEW);
		}
	}

	public boolean validate(String username, String password) {

		if ((username == null) || (password == null)) {
			return false;
		}

		if (username.equals("") || password.equals("")) {
			return false;
		}
		return true;
	}

}
