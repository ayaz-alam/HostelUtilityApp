package com.code_base_update.beans;

public class ApplicationBean {

    private long applicationId;
    private String applicationDomain;
    private String subject;
    private String description;
    private long  initDate;
    private boolean isAccepted;
    private long acceptedOn;
    private String optionalDescription;
    private String studentId;

    public ApplicationBean(long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationDomain() {
        return applicationDomain;
    }

    public void setApplicationDomain(String applicationDomain) {
        this.applicationDomain = applicationDomain;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getInitDate() {
        return initDate;
    }

    public void setInitDate(long initDate) {
        this.initDate = initDate;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public long getAcceptedOn() {
        return acceptedOn;
    }

    public void setAcceptedOn(long acceptedOn) {
        this.acceptedOn = acceptedOn;
    }

    public String getOptionalDescription() {
        return optionalDescription;
    }

    public void setOptionalDescription(String optionalDescription) {
        this.optionalDescription = optionalDescription;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
