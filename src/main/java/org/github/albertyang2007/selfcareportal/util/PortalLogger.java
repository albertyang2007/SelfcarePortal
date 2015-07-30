package org.github.albertyang2007.selfcareportal.util;

import java.io.IOException;
import java.util.logging.*;

/**
 * Logger utility for self-care portal.
 */
public final class PortalLogger {
	
	private static final int LOG_ROTATING_NUMBER = 10;
	
	private static final String LOGGER_NAME = "org.github.albertyang2007.selfcareportal";
	private static final String LOG_FILE_NAME = "selfcareportal.log";
	
	private static Logger myLogger = null;		
	private static PortalLogger portalLogger = new PortalLogger();
			
	private PortalLogger() {
		myLogger = Logger.getLogger(LOGGER_NAME);
		
		StringBuilder errMsg = new StringBuilder();
		errMsg.append("Create logger " + LOGGER_NAME + " failure! \n");
		errMsg.append("Can not create or write to file" + LOG_FILE_NAME + " in your user home directory!");
		
		FileHandler fileHandler = null;		
		try {
			fileHandler = new FileHandler("%h" + LOG_FILE_NAME, 0, LOG_ROTATING_NUMBER);
		} catch (SecurityException e) {
			// Output to the console to catch eyes
			myLogger.severe(errMsg.toString());
			
			// Reset to null to indicate the logger is not available
			myLogger = null;
		} catch (IOException e) {
			// Output to the console to catch eyes
			myLogger.severe(errMsg.toString());
			
			// Reset to null to indicate the logger is not available
			myLogger = null;
		} 

		myLogger.addHandler(fileHandler);
		
		// Not output to the console
		myLogger.setUseParentHandlers(false);
	}	
	
	/**
	 * Get the singleton of the PortalLogger.
	 * 
	 * @return PortalLogger singleton
	 */
	public static PortalLogger getLogger() {		
		return portalLogger;
	}
	
	/**
	 * Log the information of the exception to file selfcareportal.log 
	 * in home directory.
	 * 
	 * @param ex
	 *        The exception to be logged
	 * @param className
	 * 		  The name of the class
	 * @param methodName
	 * 		  The name of the method
	 * @param message
	 *        Customized message used for debugging
	 */
	public void logException(Exception ex, String className, String methodName, String message) {
		if (myLogger != null) {
			myLogger.logp(Level.SEVERE, className, methodName, message);
		}
	}

}
