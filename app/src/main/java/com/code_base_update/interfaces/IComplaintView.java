package com.code_base_update.interfaces;

import com.code_base_update.beans.ComplaintBean;

public interface IComplaintView {
    String getDomainID();

    String getSubDomain();

    String getDescription();

    long getProblemFromDate();

    ComplaintBean getComplaint();
}
