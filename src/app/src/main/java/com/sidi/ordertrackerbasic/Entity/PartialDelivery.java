package com.sidi.ordertrackerbasic.Entity;

public class PartialDelivery extends Delivery{

    private float amountToBePayed;
    private int quantity_received;

    public PartialDelivery(Delivery delivery, float amount_received, int quantity_received) {
        super(delivery.getID(), delivery.getUnit_ID(), delivery.getDelivery_status(), delivery.getDelivery_address(), delivery.getBill_no(), delivery.getAmount(), delivery.getQuantity(), delivery.getMode_of_payment(), delivery.getShopPhoneNumber());
        amountToBePayed= amount_received;
        this.quantity_received = quantity_received;
    }

    public float getAmountToBePayed() {
        return amountToBePayed;
    }

    public void setAmountToBePayed(float amountToBePayed) {
        this.amountToBePayed = amountToBePayed;
    }

    public int getQuantity_received() {
        return quantity_received;
    }

    public void setQuantity_received(int quantity_received) {
        this.quantity_received = quantity_received;
    }
}
