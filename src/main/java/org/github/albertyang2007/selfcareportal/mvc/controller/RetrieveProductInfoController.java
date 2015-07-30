package org.github.albertyang2007.selfcareportal.mvc.controller;

import javax.servlet.http.HttpSession;

import org.github.albertyang2007.selfcareportal.mvc.service.IRetrieveProductInfoService;
import org.github.albertyang2007.selfcareportal.util.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RetrieveProductInfoController {
	@Autowired
	private IRetrieveProductInfoService retrieveProductInfoService;

	private static final String VIEW = "retrieveProduct";

	@RequestMapping(value = "/retrieveProductInfo.do")
	public String processRetrieveProductInfo(HttpSession session, 
			@RequestParam(value = "page", required = false) String page,
			ModelMap model) {
		

		Integer totalPage = retrieveProductInfoService.getTotalPage();	
		
		Integer currentPage;
		if(page!=null) {
			currentPage= Integer.parseInt(page);
		}else if(session.getAttribute("currentPage")!=null)
		{
			currentPage= (Integer) session.getAttribute("currentPage");
		}else{
			currentPage=1;
		}
				
		session.setAttribute("currentPage", currentPage);
		
		Product[] products = retrieveProductInfoService.retrieveProductInfoPerPage(currentPage);
		Product[] hotProducts = retrieveProductInfoService.retrieveProductInfoPerPage(1); 
		model.addAttribute("products", products);
		model.addAttribute("hotProducts", hotProducts);
		model.addAttribute("totalPage",totalPage);
		
		return VIEW;

	}

}
