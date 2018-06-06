package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.medeveloper.ayaz.hostelutility.R;


public class MyData {


    Context context;
    static public String NAME;
    static public String HOSTELID;
    static public String ENROLLMENT_NO;
    static public String ROOM_NO;
    static public String DEPARTMENT;
    static public String EMPLOYEE_ID;
    static public String POST;
    static public String MOBILE;

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

    }
    public void saveTeacherPrefs(OfficialID id)
    {
        /**
         * Saving some data in the SharedPrefrences so that they can Accessed easily
         * For now, saving only HostelID,EmployeeID,mDepartment and RoomNo
         * */
        Log.d("Ayaz /"+"LoginAct.","Creating MyData and opening Home Page");
        savePrefs(context.getString(R.string.pref_hostel_id),context.getString(R.string.hostel_id));
        savePrefs(context.getString(R.string.pref_employee_id),id.mEmployeeId);
        savePrefs(context.getString(R.string.pref_name),id.mName);
        savePrefs(context.getString(R.string.pref_department),id.mDepartment);
        savePrefs(context.getString(R.string.pref_post),id.mPost);
        savePrefs(context.getString(R.string.pref_mobile_no),id.mPhone);

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

    }


    public void savePrefs(String Key,String Value)
    {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Key,Value).apply();
        Log.d("Ayaz","Preference Saved :"+getPrefs(Key,"-1"));
    }
    public String getPrefs(String Key,String defaultValue)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(Key, defaultValue);
    }

    public String getName()
    {
        return getPrefs(context.getString(R.string.pref_name),"NULL");
    }
    public String HostelID()
    {
        return getPrefs(context.getString(R.string.pref_name),"NULL");
    }
    public String getData(String Key)
    {
        return getPrefs(Key,"NULL");
    }



}
