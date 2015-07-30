package org.github.albertyang2007.selfcareportal.mvc.service.impl;


import org.github.albertyang2007.selfcareportal.mvc.service.IProductInfoService;
import org.github.albertyang2007.selfcareportal.mvc.service.IRetrieveProductInfoService;
import org.github.albertyang2007.selfcareportal.util.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RetrieveProductInfoServiceImpl implements
		IRetrieveProductInfoService {

	@Autowired
	private IProductInfoService productInfoService;
	
	private static final int ITEM_PER_PAGE = 10;
	
	public Product[] retrieveProductInfo() {		
		
		return productInfoService.retrieveAllProductInfo();
	}
	
	public Product[] retrieveProductInfoPerPage(Integer page){
		int lowerBound = (page-1)*ITEM_PER_PAGE;
		int upperBound = ITEM_PER_PAGE*page-1;
		return productInfoService.retrieveProductsInfo(lowerBound, upperBound);
	}

	@Override
	public Integer getTotalPage() {
		return (productInfoService.getProductCount()+ITEM_PER_PAGE-1)/ITEM_PER_PAGE;
	}

}
