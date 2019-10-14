package com.code_base_update;

public class Human {
    public static final String MALE = "male" ;
    public static final String FEMALE = "female" ;
    private String sex=MALE;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
