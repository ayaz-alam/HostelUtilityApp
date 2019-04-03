package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.medeveloper.ayaz.hostelutility.R;


public class MyData {


    public static final String ADHAAR = "pref_adhaar";
    public static final String CURRENT_STUDENT = "current_student";
    public static final String OFFICIAL_DETAILS = "current_officials";
    Context context;
    static public String NAME;
    static public String HOSTELID;
    static public String ENROLLMENT_NO;
    static public String ROOM_NO;
    static public String DEPARTMENT;
    static public String EMPLOYEE_ID;
    static public String POST;
    static public String MOBILE;
    static public String MAIL;
    static public String CURRENT_USER = "current_user";
    static public String isStudent = "is_student";
    static public String isOfficial ="is_official";

    public MyData(Context context) {
        this.context=context;
        NAME=context.getString(R.string.pref_name);
        HOSTELID=context.getString(R.string.pref_hostel_id);
        ENROLLMENT_NO=context.getString(R.string.pref_enroll);
        ROOM_NO=context.getString(R.string.pref_room);
        DEPARTMENT=context.getString(R.string.pref_department);
        EMPLOYEE_ID=context.getString(R.string.pref_employee_id);
        POST=context.getString(R.string.pref_post);
        MOBILE=context.getString(R.string.pref_mobile_no);
        MAIL =context.getString(R.string.pref_mail);


    }

    public void saveStudentPrefs(StudentDetailsClass Student)
    {
                    /**
                     * Saving some data in the SharedPrefrences so that they can Accessed easily
                     * For now, saving only HostelID,EnrollmentNo,Name and RoomNo
                     * */
                    savePrefs(context.getString(R.string.pref_hostel_id),context.getString(R.string.hostel_id));
                    savePrefs(context.getString(R.string.pref_enroll),Student.EnrollNo);
                    savePrefs(context.getString(R.string.pref_name),Student.Name);
                    savePrefs(context.getString(R.string.pref_room),Student.RoomNo);
                    savePrefs(context.getString(R.string.pref_mobile_no),Student.MobileNo);
                    savePrefs(CURRENT_STUDENT,new Gson().toJson(Student));

    }
    public void saveTeacherPrefs(OfficialsDetailsClass id)
    {
        savePrefs(OFFICIAL_DETAILS,new Gson().toJson(id));


    }


    public void clearPreferences()
    {
        savePrefs(context.getString(R.string.pref_hostel_id),null);
        savePrefs(context.getString(R.string.pref_employee_id),null);
        savePrefs(context.getString(R.string.pref_name),null);
        savePrefs(context.getString(R.string.pref_department),null);
        savePrefs(context.getString(R.string.pref_post),null);
        savePrefs(context.getString(R.string.pref_enroll),null);
        savePrefs(context.getString(R.string.pref_room),null);
        savePrefs(MOBILE,null);
        savePrefs(CURRENT_STUDENT,null);
        savePrefs(OFFICIAL_DETAILS,null);

    }


    public void savePrefs(String Key,String Value)
    {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Key,Value).apply();
    }
    public String getPrefs(String Key,String defaultValue)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(Key, defaultValue);
    }

    public void setFirstTimeUser(boolean isFirstLogin)
    {
        if(isFirstLogin)
            savePrefs("FirstTime","true");
        else
            savePrefs("FirstTime","false");

    }


    public boolean isFirstTimeUser()
    {
        if(getPrefs("FirstTime","null").equals("true"))
            return true;
        else
            return false;
    }

    public String getName()
    {
        return getPrefs(context.getString(R.string.pref_name),"NULL");
    }
    public String getData(String Key)
    {
        return getPrefs(Key,"NULL");
    }

    public void setCurrentUser(String currentUser)
    {
            savePrefs(CURRENT_USER,currentUser);
    }
    public boolean isCurrentUserIsStudent()
    {
        return getData(CURRENT_USER).equals(isStudent);
    }



}
