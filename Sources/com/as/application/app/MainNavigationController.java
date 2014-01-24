package com.as.application.app;

import com.as.application.app.Session;
import com.as.model.AdRelease;
import com.as.model.Client;
import com.as.model.Person;
import com.webobjects.appserver.WOComponent;
import com.webobjects.directtoweb.D2W;
import com.webobjects.directtoweb.D2WPage;
import com.webobjects.directtoweb.EditPageInterface;
import com.webobjects.directtoweb.ErrorPageInterface;
import com.webobjects.directtoweb.ListPageInterface;
import com.webobjects.directtoweb.QueryPageInterface;
import com.webobjects.eoaccess.EODatabaseDataSource;
import com.webobjects.eocontrol.EODataSource;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSLog;

import er.directtoweb.interfaces.ERDListPageInterface;
import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXFetchSpecification;
import er.extensions.eof.ERXQ;

public class MainNavigationController {

	private Session _session;
	public String ADRELEASE = "AdRelease";

	
	public MainNavigationController(Session s) {
		super();
		_session = s;
	}

	// NAV ACTIONS
	/*
	 * here is the original homeAction()
	 * the direct action calls listAdReleaseAction() instead of this
	 * in order to present the ad list instead of the query all page
	 */
	public WOComponent homeAction() {
        return D2W.factory().defaultPage(session());
    }

//	
//	// Ad Releases
//	
	public WOComponent listAdReleaseAction() {
		EOEditingContext ec = ERXEC.newEditingContext();
		ec.lock();
		try {
			EODatabaseDataSource ds = new EODatabaseDataSource(ec, ADRELEASE);
						
			ERXFetchSpecification<AdRelease> fs = new ERXFetchSpecification<AdRelease>(AdRelease.ENTITY_NAME, 
					ERXQ.equals(AdRelease.IS_CLOSED_KEY, false).and(AdRelease.IS_BILLBOARD.eq(false).and(AdRelease.IS_DIGITAL.eq(false))), null);
			
			ds.setFetchSpecification(fs);
			
			ERDListPageInterface lpi = (ERDListPageInterface) pageForConfigurationNamed("ListAdRelease");
			
			lpi.setDataSource(ds);

//			if(lpi instanceof D2WPage) {
//				D2WPage page = (D2WPage)lpi;
//				page.d2wContext().takeValueForKey("ListAdRelease", "navigationState");
//			}

			return (WOComponent) lpi;
		}
		finally {
			ec.unlock();
		}
	} 
	
  public WOComponent queryAdReleaseAction() {
    QueryPageInterface component = (QueryPageInterface) D2W.factory().queryPageForEntityNamed("AdRelease", session());

    return (WOComponent) component;
    
  }
 
	
	public WOComponent listDigitalAdReleaseAction() {
		NSLog.out.appendln("***top of listDigitalAdReleaseAction ");

		EOEditingContext ec = ERXEC.newEditingContext();
		ec.lock();
		try {
			EODatabaseDataSource ds = new EODatabaseDataSource(ec, ADRELEASE);
			
			//EODataSource ds = new EODataSource(ec, AdRelease.fetchAdReleases(ec, AdRelease.IS_CLOSED.eq(false), null));
			
			ERXFetchSpecification<AdRelease> fs = new ERXFetchSpecification<AdRelease>(AdRelease.ENTITY_NAME, 
					ERXQ.equals(AdRelease.IS_CLOSED_KEY, false).and(AdRelease.IS_DIGITAL.eq(true)), null);
			
			ds.setFetchSpecification(fs);
			
			//ListPageInterface lpi = (ListPageInterface) pageForConfigurationNamed("ListAdRelease");
			ERDListPageInterface lpi = (ERDListPageInterface) pageForConfigurationNamed("ListDigitalAdRelease");
			
			//lpi.setDataSource();
			lpi.setDataSource(ds);

//			if(lpi instanceof D2WPage) {
//				D2WPage page = (D2WPage)lpi;
//				page.d2wContext().takeValueForKey("ListAdReleaseDigital", "navigationState");
//			}

			return (WOComponent) lpi;
		}
		finally {
			ec.unlock();
		}
	}
	
  public WOComponent queryDigitalAction() {
    QueryPageInterface component = (QueryPageInterface) D2W.factory().queryPageForEntityNamed("DigitalAdRelease", session());

    return (WOComponent) component;
    
  }
  
	
	public WOComponent listBillboardAdRelease() {

	  EOEditingContext ec = ERXEC.newEditingContext();

	  EODatabaseDataSource ds = new EODatabaseDataSource(ec, ADRELEASE);

	  ERXFetchSpecification<AdRelease> fs = new ERXFetchSpecification<AdRelease>(AdRelease.ENTITY_NAME, 
	      ERXQ.equals(AdRelease.IS_CLOSED_KEY, false).and(AdRelease.IS_BILLBOARD.eq(true)), null);

	  ds.setFetchSpecification(fs);

	  ERDListPageInterface lpi = (ERDListPageInterface) pageForConfigurationNamed("ListBillboardAdRelease");	      
	  lpi.setDataSource(ds);

	  return (WOComponent) lpi;

	}
	 

	  public WOComponent queryBillboardAction() {
	    QueryPageInterface component = (QueryPageInterface) D2W.factory().queryPageForEntityNamed("BillboardAdRelease", session());

	    return (WOComponent) component;
	    
	  }
	  
	  
	public WOComponent listPersonAction() {

		NSLog.out.appendln("*** firing listPersonAction");

		EOEditingContext ec = ERXEC.newEditingContext();

		ListPageInterface component = D2W.factory().listPageForEntityNamed(Person.ENTITY_NAME, session());
		component.setDataSource(new EODatabaseDataSource(ec, Person.ENTITY_NAME));

		if(component instanceof D2WPage) {
			D2WPage page = (D2WPage)component;
			page.d2wContext().takeValueForKey("ListPerson", "navigationState");
		}
		return (WOComponent)component;
	}		

	public WOComponent listClientAction() {

		NSLog.out.appendln("*** firing listClientAction");

		EOEditingContext ec = ERXEC.newEditingContext();

		ListPageInterface component = D2W.factory().listPageForEntityNamed(Client.ENTITY_NAME, session());
		component.setDataSource(new EODatabaseDataSource(ec, Client.ENTITY_NAME));

		if(component instanceof D2WPage) {
			D2WPage page = (D2WPage)component;
			page.d2wContext().takeValueForKey("ListClient", "navigationState");
		}
		return (WOComponent)component;

	}

	public EditPageInterface createPersonAction() {

		NSLog.out.appendln("*** createNewUserAction");

		EditPageInterface component = D2W.factory().editPageForNewObjectWithConfigurationNamed("CreatePerson", session());

		component.setNextPage(session().context().page());

//		if(component instanceof D2WPage) {
//			D2WPage page = (D2WPage)component;
//			page.d2wContext().takeValueForKey("Person.CreatePerson", "navigationState");
//
//		}
		return component;	

	}
	
	public EditPageInterface createAdReleaseAction() {

		EditPageInterface component = D2W.factory().editPageForNewObjectWithConfigurationNamed("CreateAdRelease", session());
		component.setNextPage(session().context().page());

		return component;
	}	
	
	public EditPageInterface createClientAction() {

		EditPageInterface component = D2W.factory().editPageForNewObjectWithConfigurationNamed("CreateClient", session());
		component.setNextPage(session().context().page());

//		if(component instanceof D2WPage) {
//			D2WPage page = (D2WPage)component;
//			page.d2wContext().takeValueForKey("Client.CreateClient", "navigationState");
//
//		}
		return component;
	}	
	
	
	// GENERIC ACTIONS
	
	protected WOComponent pageForConfigurationNamed(String name) {
		WOComponent page = D2W.factory().pageForConfigurationNamed(name, session());
		return page;
	}
	
    public WOComponent queryPageForEntityName(String entityName) {
        QueryPageInterface newQueryPage = D2W.factory().queryPageForEntityNamed(entityName, session());
        return (WOComponent) newQueryPage;
    }
    
    public WOComponent newObjectForEntityName(String entityName) {
        WOComponent nextPage = null;
        try {
            EditPageInterface epi = D2W.factory().editPageForNewObjectWithEntityNamed(entityName, session());
            epi.setNextPage(session().context().page());
            nextPage = (WOComponent) epi;
        } catch (IllegalArgumentException e) {
            ErrorPageInterface epf = D2W.factory().errorPage(session());
            epf.setMessage(e.toString());
            epf.setNextPage(session().context().page());
            nextPage = (WOComponent) epf;
        }
        return nextPage;
    }
    
    // ACCESSORS
    
    public Session session() {
		return _session;
	}

	public void setSession(Session s) {
		_session = s;
	}
}
