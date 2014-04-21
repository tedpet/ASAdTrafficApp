package com.as.application.app;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Callable;


import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import tp.jasperreports.TPJRFetchSpecificationReportTask;

import com.as.model.Person;
import com.as.model.Project;
import com.as.model.ProjectPerson;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;

import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXFetchSpecification;
import er.extensions.eof.ERXSortOrdering.ERXSortOrderings;
import er.extensions.eof.qualifiers.ERXExistsQualifier;

public class Reports {

  private static File reportFile;

  /**
   * @return a {@link Callable} task that creates and returns a PDF file
   */


  public static Callable<File> createProjectReportTaskByPerson() {
    NSLog.out.appendln(" createProjectReportTaskByPerson " );

    EOEditingContext ec = ERXEC.newEditingContext();
    ec.lock();

    TPJRFetchSpecificationReportTask reportTask = null;

    try {
      
      //NSLog.out.appendln(" createProjectReportTaskByPerson in the try" );

      ERXSortOrderings sortOrderings = ProjectPerson.PERSON.dot(Person.FIRST_NAME).asc()
          .then(ProjectPerson.PERSON.dot(Person.LAST_NAME).asc())
          .then((ProjectPerson.DUE_DATE).asc());     //Movie.STUDIO.dot(_Studio.NAME).ascs();

      String reportDescription = "A report that displays all the open Projects";

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("reportDescription", reportDescription);

      EOQualifier qualifier = ProjectPerson.PROJECT.dot(Project.IS_ACTIVE.eq(true));
      ERXFetchSpecification<ProjectPerson> fs = new ERXFetchSpecification<ProjectPerson>(ProjectPerson.ENTITY_NAME, qualifier, sortOrderings);
   
      reportTask = new TPJRFetchSpecificationReportTask(fs, "ProjectReportByPerson.jasper", parameters);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();


    } finally {
      //NSLog.out.appendln("finally : "  );
      ec.unlock();
    }

    NSLog.out.appendln("about to return reportTask: " + reportTask.toString() );

    return reportTask;


  }

}
