package org.github.albertyang2007.selfcareportal.mvc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {

	private static final String VIEW = "forward:retrieveProductInfo.do";

	@RequestMapping(value = "/logout.do", method = RequestMethod.POST)
	public String processLogout(HttpSession session, ModelMap model) {
		session.invalidate();

		return VIEW;

	}
}
