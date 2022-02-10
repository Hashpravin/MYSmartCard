package com.example.mysmartcard;

public class Profile_helper {

    String name,ph,address;

    Profile_helper(){

    }

    Profile_helper(String name,String ph,String address ){
        this.name = name;
        this.ph = ph;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
