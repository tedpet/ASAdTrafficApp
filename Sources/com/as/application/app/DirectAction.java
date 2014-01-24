package com.as.application.app;

import java.util.NoSuchElementException;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;
import com.webobjects.directtoweb.D2W;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSLog;

import er.directtoweb.ERD2WDirectAction;
import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXFetchSpecification;
import er.extensions.eof.ERXQ;
import er.extensions.foundation.ERXStringUtilities;

import com.as.application.ui.components.Main;
import com.as.model.AdRelease;
import com.as.model.Person;
import com.as.application.app.Session;


public class DirectAction extends ERD2WDirectAction {
	public DirectAction(WORequest request) {
		super(request);
	}

	@Override
	public WOActionResults defaultAction() {
		return pageWithName(Main.class.getName());
	}
	
    /**
     * Checks if a page configuration is allowed to render.
     * Provide a more intelligent access scheme as the default just returns false. And
     * be sure to read the javadoc to the super class.
     * @param pageConfiguration
     * @return
     */
    protected boolean allowPageConfiguration(String pageConfiguration) {
        return false;
    }
    
	public WOActionResults loginAction() {
		
		String username = request().stringFormValueForKey("username");
		String password = request().stringFormValueForKey("password");
		String errorMessage = null;
		boolean authFailed = true;
		
		//NSLog.out.appendln("***top of loginAction in DirectAction.loginAction - username: " + username + " : password: " + password + "***");

		//Check credentials
		EOEditingContext ec = ERXEC.newEditingContext();
		Person user = null;
		
		
		if (ERXStringUtilities.stringIsNullOrEmpty(username) || ERXStringUtilities.stringIsNullOrEmpty(password)){
		
			errorMessage  =  "Please enter a username and password";

		} else {
			
			try {
		
				EOQualifier qual = Person.LOGIN.eq(username).and(Person.PASSWORD.eq(password).and(Person.IS_ACTIVE.eq(true)));
				user = Person.fetchRequiredPerson(ec, qual);			
		
				((Session)session()).setUser(user);
				authFailed = false;
				
			} catch (NoSuchElementException e) {
				//NSLog.out.appendln("*** NoSuchElementException trying   username and or password" + user);

				errorMessage = "No User found for these credentials";
				authFailed = true;
				
			} catch (Exception e) {
				NSLog.out.appendln("Bad Exception authenticating user (General Exception) -- " + e.getMessage());
				//Something bad happened
				errorMessage = "No User found for these credentials";
				//errorMessage = e.getMessage();
				authFailed = true;
			
			}
		}

		
		//Display an error message to the user.
		if(authFailed) {
			/*
			 * authentication failed so re-present the
			 * login page with the error message
			 */
			Main main = pageWithName(Main.class);
			main.setErrorMessage(errorMessage);
			return main;
		}
				
		//return D2W.factory().defaultPage(session());
		/*
		 * Karen prefers to go directly to the adList page instead of the query all page.
		 */
		

		//((Session)session()).setTheSessionsTimeout(10);
		
		return ((Session)session()).navController().listAdReleaseAction();
	}
	
}
