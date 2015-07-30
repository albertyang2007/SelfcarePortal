package org.github.albertyang2007.selfcareportal.mvc.exception;

 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.github.albertyang2007.selfcareportal.util.PortalLogger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class CustomMappingExceptionResolver extends
		SimpleMappingExceptionResolver {
	
	private static String errorTitle = "Sorry ... Error occured!!";
	
	public CustomMappingExceptionResolver() {
		super();
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		ModelAndView modelAndView = super.doResolveException(request, response,
				handler, ex);
		
		StringBuilder logMessage = new StringBuilder();
		logMessage.append("URL: " + request.getRequestURL().toString()  + "\n");
		logMessage.append("URI: " + request.getRequestURI());
		
		PortalLogger.getLogger().logException(ex, this.getClass().getName(), "doResolveException", logMessage.toString());
				
		modelAndView.getModelMap().addAttribute("errorTitle", errorTitle);
		modelAndView.getModelMap()
				.addAttribute("errorMessage", ex.getMessage());
		modelAndView.getModelMap().addAttribute("errorType", ex.getClass());
					
		return modelAndView;
	}
}
