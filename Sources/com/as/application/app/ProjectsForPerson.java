package com.as.application.app;

import com.webobjects.foundation.NSTimestamp;

public class ProjectsForPerson {
  
  public String fullName;
  public String projectTitle;
  public String projectDescription;
  public NSTimestamp dueDate;
  public String personStep;
  public String thePerson;

  
  /**
   * @return the thePerson
   */
  public String getThePerson() {
    return thePerson;
  }

  /**
   * @param thePerson the thePerson to set
   */
  public void setThePerson(String thePerson) {
    this.thePerson = thePerson;
  }

  /**
   * @return the personStep
   */
  public String getPersonStep() {
    return personStep;
  }

  /**
   * @param personStep the personStep to set
   */
  public void setPersonStep(String personStep) {
    this.personStep = personStep;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ProjectsForPerson [fullName=" + fullName + ", projectTitle=" + projectTitle + ", projectDescription=" + projectDescription + ", dueDate=" + dueDate + "]";
  }

  /**
   * @return the dueDate
   */
  public NSTimestamp getDueDate() {
    return dueDate;
  }

  /**
   * @param dueDate the dueDate to set
   */
  public void setDueDate(NSTimestamp dueDate) {
    this.dueDate = dueDate;
  }

  /**
   * @return the projectDescription
   */
  public String getProjectDescription() {
    return projectDescription;
  }

  /**
   * @param projectDescription the projectDescription to set
   */
  public void setProjectDescription(String projectDescription) {
    this.projectDescription = projectDescription;
  }

  /**
   * @return the projectTitle
   */
  public String getProjectTitle() {
    return projectTitle;
  }

  /**
   * @param projectTitle the projectTitle to set
   */
  public void setProjectTitle(String projectTitle) {
    this.projectTitle = projectTitle;
  }

  /**
   * @return the fullName
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * @param fullName the fullName to set
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

}
