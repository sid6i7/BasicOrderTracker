package com.sidi.ordertrackerbasic.Entity;

import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;

public class Notification {

    private ArrayList<String> remarks = new ArrayList<>();
    private String receiver;

    public Notification(String receiver) {
        this.receiver = receiver;
    }

    public void addMessage(String message) {
        remarks.add(message);
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public ArrayList<String> getRemarks() {
        return remarks;
    }

}
