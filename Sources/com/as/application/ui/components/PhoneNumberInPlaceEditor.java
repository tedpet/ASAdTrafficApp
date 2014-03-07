package com.as.application.ui.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSLog;

import er.ajax.AjaxUtils;
import er.extensions.components.ERXStatelessComponent;

public class PhoneNumberInPlaceEditor extends ERXStatelessComponent {
    public PhoneNumberInPlaceEditor(WOContext context) {
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
					"edits to the Pub Phone Numbers (" + e.getMessage() + ")!!');", context());

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
	
  public String multilineValue() {
    String returnString = null;

    if (object().valueForKey("comments") == null || object().valueForKey("comments") == "") {
      returnString = "---";
    } else {
      returnString = ((String) object().valueForKey("comments")).replaceAll("\n\n", "<p>").replaceAll("\n", "<br/>");
    }

    return returnString;
  }

  public void setMultilineValue(String inString) {           

    if (inString != null) {
      inString = inString.trim();

      inString = inString.replaceAll("<p>", "\r\n").replaceAll("\n", "\r\n");
    }
    object().takeValueForKey(inString, "comments");
  }

}