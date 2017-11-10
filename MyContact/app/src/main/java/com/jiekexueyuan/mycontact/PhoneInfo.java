package com.jiekexueyuan.mycontact;

/**
 * Created by stan on 2017/11/9.
 */

public class PhoneInfo {
    private String name;
    private String number;
    private long rawContactId;

    public PhoneInfo() {

    }


    public long getRawContactId() {
        return rawContactId;
    }

    public void setRawContactId(long rawContactId) {
        this.rawContactId = rawContactId;
    }

    public PhoneInfo(String name,String number) {
        setNumber(number);
        setName(name);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

}