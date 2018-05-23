package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.net.Uri;
import android.support.annotation.Keep;

import java.util.Date;

/**
 * Created by Ayaz on 5/2/2018.
 */

public class NoticeClass {

    @Keep
    public String noticeTitle,noticeBody,noticeGenerator;
    @Keep
    public String photoUrl;

    @Keep
    public Date noticeDate;

    @Keep
    public NoticeClass(String noticeTitle, String noticeBody,
                       String noticeGenerator, String photoUrl, Date noticeDate) {

        this.noticeTitle = noticeTitle;
        this.noticeBody = noticeBody;
        this.noticeGenerator = noticeGenerator;
        this.photoUrl = photoUrl;
        this.noticeDate = noticeDate;
    }

    @Keep
    public NoticeClass(String noticeTitle, String noticeBody,
                       String noticeGenerator, Date noticeDate) {
        this.noticeTitle = noticeTitle;
        this.noticeBody = noticeBody;
        this.noticeGenerator = noticeGenerator;
        this.noticeDate = noticeDate;
    }

    @Keep
    public NoticeClass() {
    }
}
