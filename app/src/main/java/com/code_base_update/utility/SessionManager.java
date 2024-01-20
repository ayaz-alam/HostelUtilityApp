package com.code_base_update.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.code_base_update.beans.CollegeBean;
import com.code_base_update.beans.HostelBean;
import com.code_base_update.beans.Student;
import com.google.gson.Gson;

public class SessionManager {

    private static final String PREF_NAME = "hostel_utility";
    private static final String COLLEGE = "clg";
    private static final String HOSTEL = "hstl";
    private static final String USER_TYPE = "user_tyep";
    private final SharedPreferences mPreferences;
    private final SharedPreferences.Editor mEditor;
    private final String COLLEGE_ID = "clg_id";
    private final String HOSTEL_ID = "hstl_id";
    private final String STUDENT = "std";
    //TODO complete this class and make sure all the names are truly random
    private Context mContext;
    public SessionManager(Context context) {
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public String getCollegeId() {
        return retrievePreferenceKeyWithValue(String.class.toString(),COLLEGE_ID).toString();
    }

    public String getHostelId() {
        return retrievePreferenceKeyWithValue(String.class.toString(),HOSTEL_ID).toString();
    }

    public String getStudentId() {
        return getStudent().getMobileNo();
    }

    public Student getStudent(){
        return (Student)retrievePreferenceKeyWithValue(Student.class.toString(),STUDENT);
    }

    public void setCollegeId(String collegeId) {
        storePreferenceKeyWithValue(String.class.toString(),COLLEGE_ID,collegeId);
    }

    public void setHostelId(String hostelId) {
        storePreferenceKeyWithValue(String.class.toString(),HOSTEL_ID,hostelId);
    }

    private void storePreferenceKeyWithValue(String classType, String key, Object val){
        if (classType.equals(Integer.class.toString()))
            mEditor.putInt(key, (Integer) val).commit();
        else if(classType.equals(Long.class.toString()))
            mEditor.putLong(key, (Long) val).commit();
        else if(classType.equals(Float.class.toString()))
            mEditor.putFloat(key, (Float) val).commit();
        else if(classType.equals(Boolean.class.toString()))
            mEditor.putBoolean(key, (Boolean) val).commit();
        else if(classType.equals(String.class.toString()))
            mEditor.putString(key, (String) val).commit();
        else if(classType.equals(Student.class.toString()))
            mEditor.putString(key,new Gson().toJson(val)).commit();
        else if(classType.equals(CollegeBean.class.toString()))
            mEditor.putString(key,new Gson().toJson(val)).commit();
        else if(classType.equals(HostelBean.class.toString()))
            mEditor.putString(key,new Gson().toJson(val)).commit();
    }

    private Object retrievePreferenceKeyWithValue(String classType, String key){
        Object valueOfKey = null;
        if(classType.equals(Integer.class.toString()))
            valueOfKey = mPreferences.getInt(key, 0);
        else if(classType.equals(Long.class.toString()))
            valueOfKey = mPreferences.getLong(key, 0L);
        else if(classType.equals(Float.class.toString()))
            valueOfKey = mPreferences.getFloat(key, 0.0f);
        else if(classType.equals(Boolean.class.toString()))
            valueOfKey = mPreferences.getBoolean(key, false);
        else if(classType.equals(String.class.toString()))
            valueOfKey = mPreferences.getString(key, "");
        else if(classType.equals(Student.class.toString())){
            String json = mPreferences.getString(key,"");
            if(json==null||json.equals(""))
                return null;
            else
                valueOfKey = new Gson().fromJson(json,Student.class);
        }else if(classType.equals(CollegeBean.class.toString())){
            String json = mPreferences.getString(key,"");
            if(json==null||json.equals(""))
                return null;
            else
                valueOfKey = new Gson().fromJson(json,CollegeBean.class);

        }else if(classType.equals(HostelBean.class.toString())){
            String json = mPreferences.getString(key,"");
            if(json==null||json.equals(""))
                return null;
            else
                valueOfKey = new Gson().fromJson(json,HostelBean.class);
        }
        return valueOfKey;
    }

    public void saveStudent(Student student) {
        storePreferenceKeyWithValue(Student.class.toString(),STUDENT,student);
    }

    public String getHostelName() {
        return getHostel().getHostelName();
    }

    public void setCollege(CollegeBean collegeBean) {
        storePreferenceKeyWithValue(CollegeBean.class.toString(),COLLEGE,collegeBean);

    }

    public void setHostel(HostelBean hostelBean) {
        storePreferenceKeyWithValue(HostelBean.class.toString(),HOSTEL,hostelBean);
    }

    public CollegeBean getCollege(){
        return (CollegeBean)retrievePreferenceKeyWithValue(CollegeBean.class.toString(),COLLEGE);
    }
    public HostelBean getHostel(){
        return (HostelBean) retrievePreferenceKeyWithValue(HostelBean.class.toString(),HOSTEL);
    }

    public void setUserType(int userType) {
        storePreferenceKeyWithValue(Integer.class.toString(),USER_TYPE,userType);
    }
    public int getUserType(){
        return (int)retrievePreferenceKeyWithValue(Integer.class.toString(),USER_TYPE);
    }

    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }
}


