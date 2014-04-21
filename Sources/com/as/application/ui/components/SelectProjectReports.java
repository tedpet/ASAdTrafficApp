package com.as.application.ui.components;

import java.io.File;
import java.util.concurrent.Callable;

import com.as.application.app.FileTaskDownloadController;
import com.as.application.app.Reports;
import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSLog;

import er.coolcomponents.CCAjaxLongResponsePage;
import er.directtoweb.pages.templates.ERD2WInspectPageTemplate;

public class SelectProjectReports extends ERD2WInspectPageTemplate {
    public SelectProjectReports(WOContext context) {
        super(context);
    }
    
    public WOActionResults projectReportByPerson() {
      // Create the task
      NSLog.out.appendln(" ready to call callable Reports.createProjectReportTask();" );

      Callable<File> reportTask = Reports.createProjectReportTaskByPerson();
      //NSLog.out.appendln(" finished to call callable Reports.createProjectReportTask();" );

      // Create the long response page
      CCAjaxLongResponsePage nextPage = pageWithName(CCAjaxLongResponsePage.class);

      // Push the task into the long response page
      nextPage.setTask(reportTask);

      // Controller for handling the Callable result in the long response page
      FileTaskDownloadController nextPageController = new FileTaskDownloadController();

      // Hyperlink text on the "Your file is downloaded page" to get back here
      nextPageController.setReturnLinkText("Project Reports");

      // The filename for the download
      nextPageController.setDownloadFileNameForProject("ProjectList by Person.pdf");


      nextPage.setNextPageForResultController(nextPageController);

      return nextPage;
    }
}