package com.sidi.ordertrackerbasic.Entity;

import java.util.ArrayList;

public class DeliveryUnit {

    private int ID;
    private String email;
    private String password;
    private String fullname;
    private String phoneNumber;
    private String status;

    public DeliveryUnit(int ID, String email, String password, String fullname, String phoneNumber, String status) {
        this.ID = ID;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

}
