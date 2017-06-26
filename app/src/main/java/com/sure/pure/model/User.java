package com.sure.pure.model;

/**
 * Created by Creative IT Works on 19-Jun-17.
 */

public class User {
    public String name;
    public String address;
    public String pin;
    public String email;
    public String phone;
    public String city;


    public User(String name, String address, String pin, String email, String password, byte[] image,String phone,String city) {
        this.name = name;
        this.address = address;
        this.pin = pin;
        this.email = email;
        this.password = password;
        this.phone=phone;
        this.image = image;
        this.city=city;
    }

    public String password;
    public byte[] image;



}
