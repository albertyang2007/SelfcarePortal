package org.github.albertyang2007.selfcareportal.mvc.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.github.albertyang2007.selfcareportal.util.PortalLogger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
	
	@Override  
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,   
	Object handler) {  		
	             
		HttpSession currSession = request.getSession();
		
		if ((currSession != null) && (currSession.getAttribute("currentUserName") != null)) {
			return true;
		}
        
        try {
			response.sendRedirect(request.getContextPath());			
		} catch (IOException ex) {													
			String logMessage = "Context Path: " + request.getContextPath();						
			PortalLogger.getLogger().logException(ex, this.getClass().getName(), "preHandle", logMessage);
							
			return false;
		}  
                
	    return false;  
	} 
	
}
