package com.as.application.ui.components;

import com.webobjects.appserver.WOContext;

import er.ajax.AjaxUtils;
import er.extensions.components.ERXStatelessComponent;
import com.webobjects.appserver.WOActionResults;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSLog;

public class CommentsInPlaceEditor extends ERXStatelessComponent {
    public CommentsInPlaceEditor(WOContext context) {
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
            
      return (object().valueForKey("comments") == null) ? null : ((String) object().valueForKey("comments")).replaceAll("\n\n", "<p>").replaceAll("\n", "<br/>");
    }
    
    public void setMultilineValue(String inString) {           
      
      inString = inString.replaceAll("<p>", "\n\n").replaceAll("\n", "\r\n");
      
      object().takeValueForKey(inString, "comments");
    }
}