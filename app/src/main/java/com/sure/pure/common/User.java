package com.sure.pure.common;

/**
 * Created by Creative IT Works on 28-Jun-17.
 */

public class User {
    public String id;
    public String name;
    public String email;
    public String mobile;
    public byte[] image;
    public String password;

    public User(String id, String name, String email, String mobile, String country,byte[] image, String city, String address, String pincode,String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.country = country;
        this.city = city;
        this.image=image;
        this.address = address;
        this.pincode = pincode;
        this.password=password;
    }

    public String country;
    public String city;
    public String address;
    public String pincode;
}
