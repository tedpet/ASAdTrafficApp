package com.as.application.app;

import com.as.model.Person;
import com.webobjects.foundation.NSLog;

import er.corebusinesslogic.ERCoreBusinessLogic;
import er.extensions.appserver.ERXSession;
import er.extensions.eof.ERXEC;

public class Session extends ERXSession {
	
	private Person _user;

	private static final long serialVersionUID = 1L;

	private MainNavigationController _navController;
	
	public Session() {
		
		//this.setTimeOut(10);
		NSLog.out.appendln("the session time out = " + this.timeOut());
	}
	
	public MainNavigationController navController() {
		if (_navController == null) {
			_navController = new MainNavigationController(this);
		}
		return _navController;
	}
	
	public Person user() {
		//NSLog.out.appendln("***the user: " + _user.fullName() + "  canEditAdRelease = " + _user.security().canEditAdRelease());

		//return Person.fetchPerson(ERXEC.newEditingContext(), Person.LOGIN.eq("t"));
		return _user;
	}
	
	public void setUser(Person user) {
		
		NSLog.out.appendln("***setUser: "+ user.fullName());
		
		_user = user;
		ERCoreBusinessLogic.setActor(user());
	}
	
    public void awake() {
        super.awake();
        if (user() != null) {
            ERCoreBusinessLogic.setActor(user());
        }
    }
    
    public void sleep() {
        ERCoreBusinessLogic.setActor(null);
        super.sleep();
    }

    public boolean hasValidUser() {
    	return user() != null;
    }

	public void setTheSessionsTimeout(double timeout) {
		
		NSLog.out.appendln("firing setTheSessionsTimeout = " + timeout);
		
		this.setTimeOut(timeout);
	}
    
}
