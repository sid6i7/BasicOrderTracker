package com.sidi.ordertrackerbasic.Entity;

public class Delivery {

    private int ID;
    private int unit_ID;
    private String delivery_status;
    private String delivery_address;
    private float amount;
    private int quantity;
    private String bill_no;
    private String mode_of_payment;
    private String shopPhoneNumber;

    public Delivery(String [] delivery_details) {
        this.ID = Integer.parseInt(delivery_details[0]);
        this.unit_ID = Integer.parseInt(delivery_details[1]);
        this.delivery_status = delivery_details[2];
        this.delivery_address = delivery_details[3];
        this.bill_no = delivery_details[4];
        this.amount = Float.parseFloat(delivery_details[5]);
        this.quantity = Integer.parseInt(delivery_details[6]);
        this.mode_of_payment = delivery_details[7];
        this.shopPhoneNumber = delivery_details[8];
    }

    public Delivery(int ID, int unit_ID, String delivery_status, String delivery_address, String bill_no, float amount, int quantity, String mode_of_payment, String shopPhoneNumber) {
        this.ID = ID;
        this.unit_ID = unit_ID;
        this.delivery_status = delivery_status;
        this.delivery_address = delivery_address;
        this.bill_no = bill_no;
        this.amount = amount;
        this.quantity = quantity;
        this.mode_of_payment = mode_of_payment;
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUnit_ID() {
        return unit_ID;
    }

    public void setUnit_ID(int unit_ID) {
        this.unit_ID = unit_ID;
    }

    public void updateDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

//    public void createDeliveryNotification(Notification notification) {
//        this.notification = notification;
//    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getMode_of_payment() {
        return mode_of_payment;
    }

    public void setMode_of_payment(String mode_of_payment) {
        this.mode_of_payment = mode_of_payment;
    }
}
