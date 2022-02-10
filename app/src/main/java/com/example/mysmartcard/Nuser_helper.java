package com.example.mysmartcard;

public class Nuser_helper {

    String name,uname,ph,pass;

    Nuser_helper(){

    }

    Nuser_helper(String name,String uname, String ph, String pass){
        this.name = name;
        this.uname = uname;
        this.ph = ph ;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
