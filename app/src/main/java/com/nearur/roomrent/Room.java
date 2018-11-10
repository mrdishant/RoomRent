package com.nearur.roomrent;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by mrdis on 8/4/2017.
 */

public class Room implements Serializable {

    int id;
    String name,mobile,address,dateofrent,hiredate,others,aadhar,rent;
    byte[] img;

    public Room(int id, String name, String mobile, String address, String dateofrent, String hiredate, String others, String aadhar, String rent, byte[] img) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.dateofrent = dateofrent;
        this.hiredate = hiredate;
        this.others = others;
        this.aadhar = aadhar;
        this.rent = rent;
        this.img = img;
    }

    public Room(){

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateofrent() {
        return dateofrent;
    }

    public void setDateofrent(String dateofrent) {
        this.dateofrent = dateofrent;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    @Override
    public String toString() {
        return "\nName :" + name +"\n"+
                "\nMobile : " + mobile  +"\n"+
                "\nAddress : " + address  +"\n"+
                "\nDate of Rent : " + dateofrent  +"\n"+
                "\nTaken Date : " + hiredate  +"\n"+
                "\nOther Members : " + others  +"\n"+
                "\nAadhar Number : " + aadhar  +"\n"+
                "\nRent : " + rent+"\n";
    }
}
