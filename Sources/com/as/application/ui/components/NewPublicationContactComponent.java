package com.as.application.ui.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.directtoweb.D2WContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSLog;

import er.ajax.AjaxUpdateContainer;
import er.ajax.AjaxUtils;
import er.extensions.components.ERXComponent;

public class NewPublicationContactComponent extends ERXComponent {
    public NewPublicationContactComponent(WOContext context) {
        super(context);
    }
    
	public EOEnterpriseObject object() {
		return (EOEnterpriseObject)valueForBinding("object");
	}

	public String key() {
		return (String)valueForBinding(key());
	}

	public String displayValue() {
		return (String)object().valueForKey(key());
	}

	public WOActionResults saveAfterLeavingInPlaceEditor() {

		//NSLog.out.appendln("***saveAfterLeavingInPlaceEditor");
		
		try {
			object().editingContext().saveChanges();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AjaxUtils.javascriptResponse("alert('There was an error trying to save the " +
					"edits to the Publication (" + e.getMessage() + ")!!');", context());

			e.printStackTrace();
		}
		
		
		//d2wContext.idForRepetitionContainer
		//AjaxUpdateContainer.updateContainerWithID(d2wContext().  .idForRepetitionContainer, context());
		
		return null;
	}
	
//    public D2WContext d2wContext() {
//    	return (D2WContext)valueForBinding("localContext");
//    }
    
    
	@Override
    public boolean synchronizesVariablesWithBindings() {
        return false;
    }

	public WOActionResults valueCancelled() {
		/*
		 * clean up and revert
		 */
		object().editingContext().revert();
		return null;
	}

}