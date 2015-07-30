package org.github.albertyang2007.selfcareportal.mvc.service.impl;

import org.github.albertyang2007.selfcareportal.mvc.service.IProductInfoService;
import org.github.albertyang2007.selfcareportal.util.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductRESTServiceImpl implements IProductInfoService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static String serverUrl = "http://localhost:8080/ProductCatalog/";
	private static String allProductsAction = "/api/products";
	private static String singleProductsAction = "/api/products/";
	private static String multipleProductsAction = "/api/products/";
	private static String productCountAction = "/api/products/count";
	private static String multipleProductsParmName = "productNo";

	/**
	 * Retrieve all products info from product server via REST.
	 * 
	 * @return All product info from product server
	 */
	@Override
	public Product[] retrieveAllProductInfo() {
		return restTemplate.getForObject(serverUrl + allProductsAction, 
				 Product[].class);
	}

	/**
	 * Retrieve single product info specified by product ID
	 * from product server via REST.
	 * 
	 * @param productID
	 *        Product ID used to retrieve the product info
	 *        
	 * @return Single product with the given product ID 
	 *         if success; null Otherwise 
	 */
	@Override
	public Product retrieveSingleProductInfo(String productID) {
		if (productID == null) {
			return null;
		}
		
		String apiUrl = serverUrl + singleProductsAction + "/" + productID;
		
		return restTemplate.getForObject(apiUrl, Product.class);	
	}

	/**
	 * Retrieve a range of product info specified by a range of
	 * product IDs from product server via REST.
	 * 
	 * @param productIDs
	 *        An array of product ID used to retrieve products info
	 * 
	 * @return An array of Product with the given product IDs
	 * 		   if success; null otherwise 
	 */
	@Override
	public Product[] retrieveProductsInfo(String[] productIDs) {
		if (productIDs == null) {
			return null;
		}
		
		String apiUrl = serverUrl + multipleProductsAction + 
					    "?" + multipleProductsParmName + "=";
		
		// Append the productIDs as parameter value
		for (int i = 0; i < productIDs.length; i++) {
			apiUrl += productIDs[i];
			
			if (i < productIDs.length-1) {
				apiUrl += ",";
			}
		}
		
		return restTemplate.getForObject(apiUrl, 
				 Product[].class);
	}

	@Override
	public int getProductCount() {
		return restTemplate.getForObject(serverUrl+productCountAction,Integer.class);
	}
	
	/**
	 * Retrieve a range of product info specified by the begin(inclusive) 
	 * and end(inclusive) index from product server via REST.
	 * 
	 * @param begin: the begin index
	 *        end:the end index
	 * 
	 * @return An array of Product within the range
	 * 		   if success; null if there's no product within the range 
	 */
	@Override
	public Product[] retrieveProductsInfo(Integer begin, Integer end) {

		String apiUrl = serverUrl + multipleProductsAction;
		if(begin!=null&&end!=null){
			apiUrl+="?begin="+begin+"&end="+end;
		}else if(begin!=null){
			apiUrl+="?begin="+begin;
		}else{
			apiUrl+="?end="+end;
		}
						
		return restTemplate.getForObject(apiUrl, 
				 Product[].class);
	}

}
