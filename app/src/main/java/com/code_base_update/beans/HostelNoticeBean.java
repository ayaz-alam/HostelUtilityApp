package com.code_base_update.beans;

public class HostelNoticeBean extends BaseBean {

    private String noticeId;
    private String noticeSubject;
    private String noticeBody;
    private String noticeAuthorId = "";
    private long timeStamp;
    private String imageUrl = "";
    private String byFaculty = "";
    private boolean hasImage = false;

    public HostelNoticeBean(String noticeId) {
        this.noticeId = noticeId;
    }

    public HostelNoticeBean() {
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeSubject() {
        return noticeSubject;
    }

    public void setNoticeSubject(String noticeSubject) {
        this.noticeSubject = noticeSubject;
    }

    public String getNoticeBody() {
        return noticeBody;
    }

    public void setNoticeBody(String noticeBody) {
        this.noticeBody = noticeBody;
    }

    public String getNoticeAuthorId() {
        return noticeAuthorId;
    }

    public void setNoticeAuthorId(String noticeAuthorId) {
        this.noticeAuthorId = noticeAuthorId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getByFaculty() {
        return byFaculty;
    }

    public void setByFaculty(String byFaculty) {
        this.byFaculty = byFaculty;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void noticeHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean hasImage() {
        if(imageUrl.equals("")) return false;
        else return true;
    }
}
