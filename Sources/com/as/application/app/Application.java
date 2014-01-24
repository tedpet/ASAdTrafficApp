package com.as.application.app;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOMessage;

import er.corebusinesslogic.ERCoreBusinessLogic;
import er.extensions.appserver.ERXApplication;
import er.extensions.appserver.navigation.ERXNavigationManager;

public class Application extends ERXApplication {
	public static void main(String[] argv) {
		ERXApplication.main(argv, Application.class);	
	}

	public Application() {
		ERXApplication.log.info("Welcome to " + name() + " !");
		setDefaultRequestHandler(requestHandlerForKey(directActionRequestHandlerKey()));
		setAllowsConcurrentRequestHandling(true);
		
		WOMessage.setDefaultEncoding("UTF8");
		
		statisticsStore().setPassword("4004");
		
	//	((Session) .session()).setTheSessionsTimeout(900);
//		session().setTheSessionsTimeout(900);
	}
	
    @Override
    public void finishInitialization() {
    	super.finishInitialization();
    	
    	// Setup main navigation
    	ERXNavigationManager.manager().configureNavigation();
    	
    }
    
    public void didFinishLaunching() {
    	super.didFinishLaunching();
    	//Setup preferences for User
    	ERCoreBusinessLogic.sharedInstance().addPreferenceRelationshipToActorEntity("Person");
    	
    	WOApplication.application().setSessionTimeOut(14400);
    }
}
