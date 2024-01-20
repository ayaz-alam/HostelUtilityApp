package com.code_base_update.utility;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class InputHelper {

    public static final int EMPTY_FIELD = 159;
    public static final int SHORT_LENGTH = 753;
    public static final int VERIFIED = 111;

    public static int verifyInputField(String text,int minLength){
        if(TextUtils.isEmpty(text)) return EMPTY_FIELD;
        else if(text.length()<minLength) return SHORT_LENGTH;
        else return VERIFIED;
    }
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String MOBILE_PATTERN ="\\d{10}";

    public static boolean verifyEMail(String email) {
         return Pattern.matches(EMAIL_PATTERN,email);
    }
    public static boolean verifyMobileNumber(String mobileNumber){
        return Pattern.matches(MOBILE_PATTERN,mobileNumber);
    }

    public static boolean checkPassword(String pass) {
        //if empty return false; and if length is less than 6 return
        // false
        return (!TextUtils.isEmpty(pass))&&!(pass.length()<6);
    }

    public static String removeDot(String email) {
        return email.replace(".","dot");

    }
}
