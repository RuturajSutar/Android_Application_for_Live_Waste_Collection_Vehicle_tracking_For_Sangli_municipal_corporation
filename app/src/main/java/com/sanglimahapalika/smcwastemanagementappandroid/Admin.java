package com.sanglimahapalika.smcwastemanagementappandroid;

import android.content.Context;
import android.content.SharedPreferences;

public class Admin {

    Context context;
    private String aname;
    SharedPreferences sharedPreferences;

    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public String getAname() {
        aname=sharedPreferences.getString("admindata","");
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
        sharedPreferences.edit().putString("admindata",aname).commit();
    }

    public Admin(Context context) {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("admininfo",Context.MODE_PRIVATE);
    }
}
