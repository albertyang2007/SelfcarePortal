package org.github.albertyang2007.selfcareportal.mvc.service;

import java.util.List;

import org.github.albertyang2007.selfcareportal.repository.entity.MyOrder;

public interface IMyProductService {
	List<MyOrder> getpurchaselist (String userID,int start,int number);
	
	int getpagenum (String userID, int numperpage);

}
