package com.nearur.roomrent;

import android.net.Uri;

public class Util {

    public  static final String Dbname="Rent";
    public  static final int dbversion=1;
    public  static final String tabname="RoomRent";
    public  static final String tabname1="Deleted";

    public static final String id="Id";
    public  static final String Name="Name";
    public  static final String Mobile="Mobile";
    public  static final String Address="Address";
    public  static final String Others="Others";
    public  static final String DateRent="DateRent";
    public  static final String HireDate="HireDate";
    public  static final String Rent="Rent";
    public  static final String Aadhar="Aadhar";
    public static final String Image="Image";


    public  static final String query="create table RoomRent ("+
            "Id integer primary key,"+
            "Name text," +
            "Mobile text," +
            "Address text," +
            "DateRent text," +
            "HireDate text," +
            "Others text," +
            "Aadhar text," +
            "Rent text," +
            "Image BLOB"+
            ")";


    public  static final String query1="create table Deleted ("+
            "Id integer primary key,"+
            "Name text," +
            "Mobile text," +
            "Address text," +
            "DateRent text," +
            "HireDate text," +
            "Others text," +
            "Aadhar text," +
            "Rent text," +
            "Image BLOB"+
            ")";

    public static final Uri u=Uri.parse("content://com.nearur.rent/"+tabname);
    public static final Uri u1=Uri.parse("content://com.nearur.rent/"+tabname1);


}
