package org.github.albertyang2007.selfcareportal.mvc.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.github.albertyang2007.selfcareportal.mvc.service.IProductInfoService;
import org.github.albertyang2007.selfcareportal.mvc.service.IPurchaseService;
import org.github.albertyang2007.selfcareportal.repository.entity.MyOrder;
import org.github.albertyang2007.selfcareportal.repository.service.DataAccessService;
import org.github.albertyang2007.selfcareportal.util.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PurchaseServiceImpl implements IPurchaseService {

	@Autowired
	private DataAccessService daoService;
	
	@Autowired
	private IProductInfoService productInfoService;
	
	/**
	 * Add an order to the user account in Database
	 * 
	 * @param userName
	 * 		  User name to identify the user. Must not be null.
	 * 
	 * @param productIDs
	 *        Items to buy. Must not be null.
	 *        
	 * @return order ID if success; null otherwise       	
	 */
	@Override
	public MyOrder makeOrder(String userName, String[] productIDs) {
		
		assert(userName != null);
		assert(productIDs != null);
		
		List<Product> productList = buildProductList(productIDs);
		
		if (productList != null) {			
			// Invoke DAO to save the order into DB	
			return daoService.addOrder(userName, productList);
		}
		
		return null;
	}
	
	/**
	 * Retrieve all products info and use product IDs to get the
	 * product info.
	 * 
	 * @param productIDs
	 *        An array of product IDs. Must not been null
	 * 
	 * @return List of Product if success; null otherwise 
	 */	
	private List<Product> buildProductList(String[] productIDs) {

		Product[] latestProducts = productInfoService.retrieveAllProductInfo();
		if (latestProducts != null ) {
			// Use productID as key to construct a map 			
			Map<String, Product> productsMap = new HashMap<String, Product>(latestProducts.length);		
			for (Product product : latestProducts) {
				productsMap.put(product.getProductNo(), product);
			}
			
			List<Product> productList = new ArrayList<Product>(productIDs.length);
			
			// Retrieve product info from the map
			for (int i = 0; i < productIDs.length; i++) {
				Product item = productsMap.get(productIDs[i]);

				if (item == null) {	
					// if any mapping fail, return null
					return null;
				}

				productList.add(item);						
			}
			
			return productList;
		}			

		return null;
	}
	

}
