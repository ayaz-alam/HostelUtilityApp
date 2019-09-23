package com.code_base_update.beans;

public class ComplaintBean {

    private String studentId, complaintId, complaintDomainId, complaintSubDomain, complaintDescription;
    private long timeStamp;
    private long problemFacingFromDate;
    private boolean complaintStatus;
    private long resolvedOnDate;

    public ComplaintBean() {

    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getComplaintDomainId() {
        return complaintDomainId;
    }

    public void setComplaintDomainId(String complaintDomainId) {
        this.complaintDomainId = complaintDomainId;
    }

    public String getComplaintSubDomain() {
        return complaintSubDomain;
    }

    public void setComplaintSubDomain(String complaintSubDomain) {
        this.complaintSubDomain = complaintSubDomain;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getProblemFacingFromDate() {
        return problemFacingFromDate;
    }

    public void setProblemFacingFromDate(long problemFacingFromDate) {
        this.problemFacingFromDate = problemFacingFromDate;
    }

    public boolean isComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(boolean complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public long getResolvedOnDate() {
        return resolvedOnDate;
    }

    public void setResolvedOnDate(long resolvedOnDate) {
        this.resolvedOnDate = resolvedOnDate;
    }
}
