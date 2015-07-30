
package org.github.albertyang2007.selfcareportal.mvc.service;

import org.github.albertyang2007.selfcareportal.repository.entity.MyOrder;

/**
 * Provide service to purchase items
 */
public interface IPurchaseService {
	
	/**
	 * Add an order to the user account
	 * 
	 * @param userName
	 * 		  User name to identify the user. Must not be null.
	 * 
	 * @param productIDs
	 *        Items to buy. Must not be null.
	 *        
	 * @return order ID if success; null otherwise       	
	 */
	MyOrder makeOrder(String userName, String[] productIDs);
}
