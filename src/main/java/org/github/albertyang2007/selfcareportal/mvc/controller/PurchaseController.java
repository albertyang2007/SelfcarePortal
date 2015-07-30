package org.github.albertyang2007.selfcareportal.mvc.controller;


import java.util.ArrayList;
import java.util.List;

import org.github.albertyang2007.selfcareportal.mvc.service.IPurchaseService;
import org.github.albertyang2007.selfcareportal.repository.entity.MyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
 
@Controller
@SessionAttributes("currentUserName")
public class PurchaseController {
	@Autowired
	private IPurchaseService purchaseService;
	 
	private static final String SUCCESS_VIEW = "myproductlist";
	private static final String FAIL_VIEW = "purchaseFail";
			
	private static final String FAIL_REASON_EMPTY_ORDER = "The order is empty!";
	private static final String FAIL_REASON_MISC_ERROR = "Misc. Error!";
	
	@RequestMapping(value = "/purchase.do", method = RequestMethod.POST)
	public String processPurchaseOrder(@ModelAttribute("currentUserName") String name, 										   
									   @RequestParam(value="productID", required=false) String[] productIDs, 			                           
			                           ModelMap model) {										
		// 	Check whether order is empty
		if (productIDs == null) {
			model.addAttribute("failReason", FAIL_REASON_EMPTY_ORDER);
			return FAIL_VIEW;
		}
					
		MyOrder myOrder = purchaseService.makeOrder(name, productIDs);
					
		if (myOrder == null) {
			model.addAttribute("failReason", FAIL_REASON_MISC_ERROR);
			return FAIL_VIEW;
		}		
		
		// Construct the order list to fit into the expectation of
		// the attribute "myorderlist"
		List<MyOrder> myOrderList = new ArrayList<MyOrder>();
		myOrderList.add(myOrder);
		
		model.addAttribute("myorderlist", myOrderList);
		model.addAttribute("requestSource", "purchase");
		
		return SUCCESS_VIEW;
	}
		 	

}
