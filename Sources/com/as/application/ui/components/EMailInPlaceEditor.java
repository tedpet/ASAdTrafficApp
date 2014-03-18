package com.as.application.ui.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSLog;

import er.ajax.AjaxUtils;
import er.extensions.components.ERXComponent;

public class EMailInPlaceEditor extends ERXComponent {
    public EMailInPlaceEditor(WOContext context) {
        super(context);
    }
    
	public EOEnterpriseObject object() {
		return (EOEnterpriseObject)valueForBinding("object");
	}

	public String key() {
		return (String)valueForBinding(key());
	}

	public String displayValue() {
	  String returnString = "";
	  if ((String)object().valueForKey("eMail") != null) {
	    returnString = (String)object().valueForKey("eMail");
	  } else {
	    returnString = "---";
	  }
		return returnString;
	}
	
	public void setDisplayValue(String s) {
	  if (s != null) {
	    object().takeValueForKey("eMail", s);
	  } else {
	    object().takeValueForKey("eMail", "---");
	  }
	}

	public WOActionResults saveAfterLeavingInPlaceEditor() {

		//NSLog.out.appendln("***saveAfterLeavingInPlaceEditor");
		
		try {
			object().editingContext().saveChanges();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AjaxUtils.javascriptResponse("alert('There was an error trying to save the " +
					"edits to the EMail addresses (" + e.getMessage() + ")!!');", context());

			e.printStackTrace();
		}

		return null;
	}
	
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