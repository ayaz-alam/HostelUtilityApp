package com.medeveloper.ayaz.hostelutility.classes_and_adapters;

import androidx.annotation.Keep;

/**
 * Created by Ayaz on 5/20/2018.
 */

public class NetRefClass {

    @Keep
    public String ID,Email;

    @Keep
    public NetRefClass() {
    }

    @Keep
    public NetRefClass(String ID, String email) {

        this.ID = ID;
        Email = email;
    }
}
