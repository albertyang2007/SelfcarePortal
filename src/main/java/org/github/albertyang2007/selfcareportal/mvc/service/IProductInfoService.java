package org.github.albertyang2007.selfcareportal.mvc.service;

import org.github.albertyang2007.selfcareportal.util.Product;

/**
 * Provide APIs/service to retrieve product info
 */
public interface IProductInfoService {
	
	/**
	 * get all the product count
	 * 
	 * @return the number of product count 
	 */
	int getProductCount();
	
	/**
	 * Retrieve a range of product info specified by the begin
	 * and end index
	 * 
	 * 
	 * @return An array of Product within the range
	 * 		   if success; null if the range is invalid 
	 */
	Product[] retrieveProductsInfo(Integer begin, Integer end);
	
	/**
	 * Retrieve all products info.
	 * 
	 * @return All product info 
	 */
	Product[] retrieveAllProductInfo();
	
	/**
	 * Retrieve single product info specified by product ID.
	 * 
	 * @param productID
	 *        Product ID used to retrieve the product info
	 *        
	 * @return Single product with the given product ID 
	 *         if success; null Otherwise 
	 */
	Product retrieveSingleProductInfo(String productID);
	
	/**
	 * Retrieve a range of product info specified by a range of
	 * product IDs.
	 * 
	 * @param productIDs
	 *        An array of product ID used to retrieve products info
	 * 
	 * @return An array of Product with the given product IDs
	 * 		   if success; null otherwise 
	 */
	Product[] retrieveProductsInfo(String[] productIDs);	
}
