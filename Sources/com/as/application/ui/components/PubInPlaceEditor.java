package com.as.application.ui.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEnterpriseObject;

import er.ajax.AjaxUtils;
import er.extensions.components.ERXStatelessComponent;

public class PubInPlaceEditor extends ERXStatelessComponent {
	
	//private String _key;
	
    public PubInPlaceEditor(WOContext context) {
        super(context);
    }
    
	public EOEnterpriseObject object() {
		return (EOEnterpriseObject)valueForBinding("object");
	}

	public String key() {
		return (String)valueForBinding(key());
	}
	
//	public void setKey(String theKey) {
//		
//	}

	public String displayValue() {
		return (String)object().valueForKey(key());
	}

	public WOActionResults saveAfterLeavingInPlaceEditor() {

		try {
			object().editingContext().saveChanges();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AjaxUtils.javascriptResponse("alert('There was an error trying to save the " +
					"edits to the Pubs (" + e.getMessage() + ")!!');", context());

			e.printStackTrace();
		}
		
		return null;
	}
}