package com.sanglimahapalika.smcwastemanagementappandroid;

public class TodaysComplaintClass {
    private String Name;
    private String Mobile;
    private String Address;
    private String Discription;

    public TodaysComplaintClass() {
    }

    public TodaysComplaintClass(String name, String mobile, String address, String discription) {
        Name = name;
        Mobile = mobile;
        Address = address;
        Discription = discription;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }
}
