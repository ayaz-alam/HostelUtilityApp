package com.code_base_update.beans;

import java.util.Calendar;

public class ApplicationBean extends BaseBean{

    public static final int STATUS_WAITING = 0;
    public static final int STATUS_SEEN = 1;
    public static final int STATUS_ACCEPTED = 2;
    public static final int STATUS_REJECTED = 3;
    public static final int STATUS_WITHDRAWN = 4;

    private long applicationId;
    private String applicationDomain;
    private String subject;
    private String description;
    private long  timeStamp;
    private String optionalDescription;
    private String studentId;
    private int status  = STATUS_WAITING;
    private long statusTime;
    private String statusDescription = "Not seen";
    private long revokedOn;
    private boolean isActive = true;

    //REQUIRED
    public ApplicationBean() {
    }

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

    public String getOptionalDescription() {
        return optionalDescription;
    }

    public void setOptionalDescription(String optionalDescription) {
        this.optionalDescription = optionalDescription;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
        this.revokedOn = timeStamp;
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

    public void setAccepted(){
        status = STATUS_ACCEPTED;
        statusDescription =  "Accepted";
        statusTime = Calendar.getInstance().getTimeInMillis();
    }

    public void setRejected(String reason){
        status = STATUS_REJECTED;
        statusDescription =  reason;
        isActive =false;
        statusTime = Calendar.getInstance().getTimeInMillis();
    }

    public void setWithdrawn(){
        status = STATUS_WITHDRAWN;
        statusDescription =  "Application withdrawn";
        isActive = false;
        statusTime = Calendar.getInstance().getTimeInMillis();
    }

    public void setSeen(){
        status = STATUS_SEEN;
        statusDescription =  "Application seen";
        statusTime = Calendar.getInstance().getTimeInMillis();
    }


    public int getStatus() {
        return status;
    }

    public long getStatusTime() {
        return statusTime;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public long getRevokedOn() {
        return revokedOn;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setReminder() {
        revokedOn = Calendar.getInstance().getTimeInMillis();
    }
}
