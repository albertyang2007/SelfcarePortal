package org.github.albertyang2007.selfcareportal.mvc.service.impl;


import java.util.List;

import org.github.albertyang2007.selfcareportal.mvc.service.IMyProductService;
import org.github.albertyang2007.selfcareportal.repository.entity.MyOrder;
import org.github.albertyang2007.selfcareportal.repository.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyProductService implements IMyProductService{
	
	@Autowired
	private DataAccessService service;
	
    public List<MyOrder> getpurchaselist (String userID,int currentpage,int numperpage){

		int	start = (currentpage - 1) * numperpage;
	  
    	try{
	       return service.findOrdersbypage(userID, start, numperpage);
         }catch(Exception e){
		   return null;
		}
	}
    
    public int getpagenum (String userID, int numperpage){
       try{
    	   long ordernum = service.findOrdersNum(userID);
    	   int div = ((int)ordernum) / numperpage;
    	   int mod = ((int)ordernum) % numperpage;
    	   return (mod == 0) ? div : (div + 1);
       }catch(Exception e){
    	   return 0;
       }
    }
		

}
