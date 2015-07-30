package org.github.albertyang2007.selfcareportal.mvc.service;


import org.github.albertyang2007.selfcareportal.util.Product;

public interface IRetrieveProductInfoService {
	Product[] retrieveProductInfo();

	Integer getTotalPage();	

	Product[] retrieveProductInfoPerPage(Integer currentPage);
}
