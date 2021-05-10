package com.sanglimahapalika.smcwastemanagementappandroid;

import android.content.Context;
import android.content.SharedPreferences;

public class Driver {

    Context context;
    private String dname;
    SharedPreferences sharedPreferences;



    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public String getDname() {
        dname=sharedPreferences.getString("driverdata","");
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
        sharedPreferences.edit().putString("driverdata",dname).commit();
    }

    public Driver(Context context) {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("driverinfo",Context.MODE_PRIVATE);
    }
}
