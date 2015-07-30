package org.github.albertyang2007.selfcareportal.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.github.albertyang2007.selfcareportal.mvc.service.IMyProductService;
import org.github.albertyang2007.selfcareportal.repository.entity.MyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;






@Controller
public class MyProductController {
	@Autowired
	private IMyProductService myproductservice;

	private static final String VIEW = "myproductlist";
	private static final int NUMPERPAGE = 5;

	@RequestMapping(value = "/myproductlist.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String getProductList(HttpSession session,
			                     @RequestParam(value = "page", required = false) String page,
			                     ModelMap model) {

		List<MyOrder> myorderlist = null;
		int currentpage = 1;
		
		// The user have been authorized at intercepter, we can
		// Safely assume it is valid user.
		String name = (String) session.getAttribute("currentUserName");
		
		
		if (page != null){
		   currentpage = Integer.parseInt(page);
		}
		
		int numofpage = myproductservice.getpagenum(name,NUMPERPAGE);
		
		if (numofpage != 0){
		   myorderlist = myproductservice.getpurchaselist(name,currentpage,NUMPERPAGE);
		}

		session.setAttribute("currentpage",currentpage);
		model.addAttribute("myorderlist", myorderlist);
		model.addAttribute("totalpage", numofpage);

		return VIEW;

	}

}
