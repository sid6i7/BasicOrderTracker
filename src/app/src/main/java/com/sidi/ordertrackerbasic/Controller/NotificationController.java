package com.sidi.ordertrackerbasic.Controller;

import android.telephony.SmsManager;

import com.sidi.ordertrackerbasic.Entity.Notification;

import java.util.ArrayList;

public class NotificationController {

    public static void sendTextNotification(Notification notification) {
        SmsManager smsManager = SmsManager.getDefault();
        for (String message: notification.getRemarks()) {
            smsManager.sendTextMessage(notification.getReceiver(), null, message, null, null);
        }
    }

    public static void sendText(String receiver, ArrayList<String> messages) {
        SmsManager smsManager = SmsManager.getDefault();
        for (String message: messages) {
            smsManager.sendTextMessage(receiver, null, message, null, null);
        }
    }

}
