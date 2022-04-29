package com.sidi.ordertrackerbasic.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import com.sidi.ordertrackerbasic.Boundry.OTPVerificationGUI;
import com.sidi.ordertrackerbasic.Entity.Notification;
import com.sidi.ordertrackerbasic.Entity.PartialDelivery;

import com.sidi.ordertrackerbasic.Entity.Delivery;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class DeliveryController {


    private Context context;
    private ArrayList<Delivery> deliveryList;
    DataController dc;

    public DeliveryController(Context context, DataController dc) {
        this.context = context;
        this.dc = dc;
    }

    public DeliveryController(Context context, ArrayList<Delivery> deliveryList) {
        this.context = context;
        this.deliveryList = deliveryList;
        dc = new DataController(context);
    }

    public void sendDeliveryTextToShopOwner(Delivery delivery, String MOP, String deliveryStatus, Activity activity) {
        SmsManager smsManager = SmsManager.getDefault();
        String message = "Your delivery is here\nPlease verify the details\nQuantity to be received: " + delivery.getQuantity() + "\nAmount to be paid: " + delivery.getAmount() + "\nYour will soon get an OTP, tell the OTP to the delivery unit only if the details are correct";
        try {
            smsManager.sendTextMessage(delivery.getShopPhoneNumber(), null, message, null, null);
            Log.d("TAG", "sendDeliveryTextToShopOwner: ");
        } catch (Exception e) {
            Log.d("Send text error..", "sendDeliveryTextToShopOwner: " + e.getMessage());
        }
    }

    public void displayVerifyOTPpage(PartialDelivery delivery, String otp, Activity activity) {

        Intent intent = new Intent(activity.getApplicationContext(), OTPVerificationGUI.class);
        String [] delivery_details = new String[] {String.valueOf(delivery.getID()), String.valueOf(delivery.getUnit_ID()), delivery.getDelivery_status(), delivery.getDelivery_address(), delivery.getBill_no(), String.valueOf(delivery.getAmount()), String.valueOf(delivery.getQuantity()), delivery.getMode_of_payment(), delivery.getShopPhoneNumber(), String.valueOf(delivery.getAmountToBePayed()), String.valueOf(delivery.getQuantity_received())};
        intent.putExtra("delivery_details", delivery_details);
        activity.startActivity(intent);
        activity.finish();
    }

    public void displayVerifyOTPpage(Delivery delivery, String otp, Activity activity) {

        Intent intent = new Intent(activity.getApplicationContext(), OTPVerificationGUI.class);
        String [] delivery_details = new String[] {String.valueOf(delivery.getID()), String.valueOf(delivery.getUnit_ID()), delivery.getDelivery_status(), delivery.getDelivery_address(), delivery.getBill_no(), String.valueOf(delivery.getAmount()), String.valueOf(delivery.getQuantity()), delivery.getMode_of_payment(), delivery.getShopPhoneNumber()};
        intent.putExtra("delivery_details", delivery_details);
        activity.startActivity(intent);
        activity.finish();
    }

    public String generateDeliveryOTP() {
        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        return otp;
    }

    public Notification createShopOwnerNotification(PartialDelivery delivery, String otp) {
        String message_1 = "Your delivery is here\n-------------------\nPlease verify the details\nQuantity to be received: " + delivery.getQuantity_received() + "\nAmount to be paid: " + delivery.getAmountToBePayed() + "\nMOD: "+ delivery.getMode_of_payment() + "\nYou will soon get an OTP";
        String message_2 = "\nTell the OTP to the delivery unit only if the details are correct\n--------\nOTP: "+ otp;
        Notification notification = new Notification(delivery.getShopPhoneNumber());
        notification.addMessage(message_1);
        notification.addMessage(message_2);

        return notification;
    }

    public Notification createShopOwnerNotification(Delivery delivery, String otp) {
        String message_1 = "Your delivery is here\n-------------------\nPlease verify the details\nQuantity to be received: " + delivery.getQuantity() + "\nAmount to be paid: " + delivery.getAmount() + "\nYou will soon get an OTP";
        String message_2 = "\nTell the OTP to the delivery unit only if the details are correct\n--------\nOTP: "+ otp;
        Notification notification = new Notification(delivery.getShopPhoneNumber());
        notification.addMessage(message_1);
        notification.addMessage(message_2);
        return notification;
    }

    public Notification createOwnerNotification(Delivery delivery){
        String message = "Delivery Number: " + delivery.getID() + "\nDelivered to " + delivery.getDelivery_address() + "\nBy Unit number: " + delivery.getUnit_ID() + "\nStatus: " + delivery.getDelivery_status();
        Notification notification = new Notification("distribution_owner_phone_number");
        notification.addMessage(message);
        return notification;
    }
    public Notification createOwnerNotification(PartialDelivery delivery){
        String message = "Delivery #" + delivery.getID() + " delivered to " + delivery.getDelivery_address() + " by unit #" + delivery.getUnit_ID() + "\nStatus: " + delivery.getDelivery_status() + "\nAmount received: " + delivery.getAmountToBePayed()+ "\nQuantity received: " + delivery.getAmountToBePayed();
        Notification notification = new Notification("distribution_owner_phone_number");
        notification.addMessage(message);
        return notification;
    }

    public void sendDeliveryOTP(int delivery_ID, String MOP, float amount_received, int quantity_received, Activity activity)
    {
        Delivery delivery = dc.fetchDelivery(delivery_ID);
        delivery.setMode_of_payment(MOP);
        delivery.setDelivery_status("partial");
        PartialDelivery partialDelivery = new PartialDelivery(delivery, amount_received, quantity_received); // just one extra LOC
        String otp = generateDeliveryOTP();
        try {
            Notification notification = createShopOwnerNotification(partialDelivery, otp);
            NotificationController.sendTextNotification(notification);
            System.out.println(partialDelivery.getShopPhoneNumber() + " : " + otp);
            displayVerifyOTPpage(partialDelivery, otp, activity);
            Log.d("OTP success", "sendOTP- delivery_ID: " + delivery_ID);
        } catch (Exception e) {
            Log.d("OTP error", "sendOTP: " + e.getMessage());
        }
    }

    public void sendDeliveryOTP(int delivery_ID, String modeOfPayment, Activity activity) { //Todo: Rename this function
        Delivery delivery = dc.fetchDelivery(delivery_ID);
        delivery.setMode_of_payment(modeOfPayment);
        delivery.setDelivery_status("complete");
        String otp = generateDeliveryOTP();
        try {
            Notification notification = createShopOwnerNotification(delivery, otp);
            NotificationController.sendTextNotification(notification);
            System.out.println(delivery.getShopPhoneNumber() + " : " + otp);
            displayVerifyOTPpage(delivery, otp, activity);
            Log.d("OTP success", "sendOTP- delivery_ID: " + delivery_ID);
        } catch (Exception e) {
            Log.d("OTP error", "sendOTP: " + e.getMessage());
        }
    }

    public void confirmDelivery(Delivery delivery) {
        System.out.println("outside of if statement");
        try {
            dc.updateDelivery(delivery, delivery.getDelivery_status());
            Notification notification = createOwnerNotification(delivery);
            NotificationController.sendTextNotification(notification);
            System.out.println("inside of confirm delivery");
            Log.d("Delivery Status", "Changed delivery status of " + delivery.getID() + " to complete" +"\n Mode of payment: " + delivery.getMode_of_payment());
        }catch(Exception e) {
            Log.d("confirm delivery error", "confirmDelivery: " + e.getMessage());
        }
    }

    public void confirmDelivery(PartialDelivery delivery) {
        try {
            dc.updateDelivery(delivery, delivery.getDelivery_status());
            Log.d("Delivery Status", "Changed delivery status of " + delivery.getID() + " to " + "partial"+"\n Mode of payment: " + delivery.getMode_of_payment());
        }catch(Exception e) {
            Log.d("confirm delivery error", "confirmDelivery: " + e.getMessage());
        }
    }

   public void cancelDelivery(Delivery delivery, String remarks) {
        dc.updateDelivery(delivery, "cancelled");
        ArrayList<String> messages = new ArrayList<>();
        String message = "Delivery with bill no" + delivery.getBill_no() + " cancelled";
        messages.add(message);
        message = "Remarks: " + remarks;
        messages.add(message);
        NotificationController.sendText("distribution_owner_phone_number", messages);
   }

}
