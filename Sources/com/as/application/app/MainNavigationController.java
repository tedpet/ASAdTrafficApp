package com.as.application.app;

import com.as.application.app.Session;
import com.as.model.AdRelease;
import com.as.model.Client;
import com.as.model.Person;
import com.as.model.Project;
import com.as.model.StudioNumber;
import com.webobjects.appserver.WOComponent;
import com.webobjects.directtoweb.D2W;
import com.webobjects.directtoweb.D2WPage;
import com.webobjects.directtoweb.EditPageInterface;
import com.webobjects.directtoweb.ErrorPageInterface;
import com.webobjects.directtoweb.InspectPageInterface;
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
  public String PERSON = "Person";
  public String CLIENT = "Client";
  public String STUDIONUMBER = "StudioNumber";
  public String PROJECT = "Project";
	
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

	    EODatabaseDataSource ds = new EODatabaseDataSource(ERXEC.newEditingContext(), PERSON);

	    ds.setFetchSpecification(new ERXFetchSpecification<Person>(Person.ENTITY_NAME, Person.IS_ACTIVE.eq(true), null));

	    ERDListPageInterface lpi = (ERDListPageInterface) pageForConfigurationNamed("ListPerson");

	    lpi.setDataSource(ds);

	    return (WOComponent)lpi;
	  }
	  
	  public WOComponent queryPersonAction() {
	    QueryPageInterface component = (QueryPageInterface) D2W.factory().queryPageForEntityNamed("Person", session());

	    return (WOComponent) component;

	  }

	public WOComponent listClientAction() {

    EODatabaseDataSource ds = new EODatabaseDataSource(ERXEC.newEditingContext(), CLIENT);

    ds.setFetchSpecification(new ERXFetchSpecification<Client>(Client.ENTITY_NAME, Client.IS_ACTIVE.eq(true), null));

    ERDListPageInterface lpi = (ERDListPageInterface) pageForConfigurationNamed("ListClient");

    lpi.setDataSource(ds);

    return (WOComponent)lpi;

	}

  public WOComponent queryClientAction() {
    QueryPageInterface component = (QueryPageInterface) D2W.factory().queryPageForEntityNamed("Client", session());

    return (WOComponent) component;

  }
  
	public EditPageInterface createPersonAction() {

		EditPageInterface component = D2W.factory().editPageForNewObjectWithConfigurationNamed("CreatePerson", session());

		component.setNextPage(session().context().page());

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

		return component;
	}	
	
//
//// Studio Numbers
//
public WOComponent listStudioNumberAction() {
  EOEditingContext ec = ERXEC.newEditingContext();
  ec.lock();
  try {
    EODatabaseDataSource ds = new EODatabaseDataSource(ec, STUDIONUMBER);
          
    ERXFetchSpecification<StudioNumber> fs = new ERXFetchSpecification<StudioNumber>(StudioNumber.ENTITY_NAME, 
        StudioNumber.IS_ACTIVE.eq(true), null);
    
    ds.setFetchSpecification(fs);
    
    ERDListPageInterface lpi = (ERDListPageInterface) pageForConfigurationNamed("ListStudioNumber");
    
    lpi.setDataSource(ds);

    return (WOComponent) lpi;
  }
  finally {
    ec.unlock();
  }
} 


public EditPageInterface createStudioNumberAction() {

  EditPageInterface component = D2W.factory().editPageForNewObjectWithConfigurationNamed("CreateStudioNumber", session());
  component.setNextPage(session().context().page());

  return component;
} 

//    searchStudioNumbers

public WOComponent searchStudioNumber() {
  QueryPageInterface component = (QueryPageInterface) D2W.factory().queryPageForEntityNamed("StudioNumber", session());

  return (WOComponent) component;

}


//   Projects

public WOComponent listProjectAction() {
  EOEditingContext ec = ERXEC.newEditingContext();
  ec.lock();
  try {
    EODatabaseDataSource ds = new EODatabaseDataSource(ec, PROJECT);
          
    ERXFetchSpecification<Project> fs = new ERXFetchSpecification<Project>(Project.ENTITY_NAME, 
        Project.IS_ACTIVE.eq(true), null);
    
    ds.setFetchSpecification(fs);
    
    ERDListPageInterface lpi = (ERDListPageInterface) pageForConfigurationNamed("ListProject");
    
    lpi.setDataSource(ds);

    return (WOComponent) lpi;
  }
  finally {
    ec.unlock();
  }
} 


public EditPageInterface createProjectAction() {

  EditPageInterface component = D2W.factory().editPageForNewObjectWithConfigurationNamed("CreateProject", session());
  component.setNextPage(session().context().page());

  return component;
} 

//    searchStudioNumbers

public WOComponent searchProject() {
  QueryPageInterface component = (QueryPageInterface) D2W.factory().queryPageForEntityNamed("Project", session());

  return (WOComponent) component;

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
    
    
    /*
     * bring in the report section for reports
     */
    public WOComponent showProjectReportSelectionAction() {
      
      return D2W.factory().pageForConfigurationNamed("SelectProjectReports", session());
      
    }

    // ACCESSORS
    
    public Session session() {
		return _session;
	}

	public void setSession(Session s) {
		_session = s;
	}
}
