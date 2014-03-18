package com.as.application.ui.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.directtoweb.D2WContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSLog;

import er.ajax.AjaxUpdateContainer;
import er.ajax.AjaxUtils;
import er.extensions.components.ERXComponent;
import er.extensions.components.ERXStatelessComponent;

public class PubContactInPlaceEditor extends ERXStatelessComponent {
    public PubContactInPlaceEditor(WOContext context) {
        super(context);
    }
    
    public EOEnterpriseObject object() {
      return (EOEnterpriseObject)valueForBinding("object");
    }

    public String key() {
      return (String)valueForBinding(key());
    }
    
//    public void setKey(String theKey) {
//      
//    }

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
    
    public String multilineValue() {
      String returnString = null;

      if (object().valueForKey("pubContact") == null || object().valueForKey("pubContact") == "") {
        returnString = "---";
      } else {
        returnString = ((String) object().valueForKey("pubContact")).replaceAll("\n\n", "<p>").replaceAll("\n", "<br/>");
      }

      return returnString;
    }

    public void setMultilineValue(String inString) {           

      if (inString != null) {
        inString = inString.trim();

        inString = inString.replaceAll("<p>", "\r\n").replaceAll("\n", "\r\n");
      }
      object().takeValueForKey(inString, "pubContact");
    }

}