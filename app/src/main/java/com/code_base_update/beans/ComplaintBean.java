package com.code_base_update.beans;

import com.code_base_update.Constants;

import java.util.ArrayList;

public class ComplaintBean extends BaseBean{

    private String studentId, complaintId, complaintDomainId;
    private String descriptions;
    private long timeStamp;
    private long problemFacingFromDate;
    private boolean complaintStatus;
    private long resolvedOnDate;
    private String optionalDescription;

    public ComplaintBean() {

    }

    public static ComplaintBean getLoadingComponent() {
        ComplaintBean complaintBean =new ComplaintBean();
        complaintBean.setComplaintId(Constants.LOADING_ITEM);
        return complaintBean;
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

    public String getDescriptions() {
        return descriptions;
    }

    public String getDetails(){
        return descriptions;
    }


    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public void setOptionalDescription(String optionalDescription) {
        this.optionalDescription = optionalDescription;
    }

    public String getOptionalDescription() {
        return optionalDescription;
    }
}
