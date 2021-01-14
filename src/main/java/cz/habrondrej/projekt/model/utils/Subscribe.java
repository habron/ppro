package cz.habrondrej.projekt.model.utils;

import cz.habrondrej.projekt.model.Subscription;

import java.sql.Timestamp;

public class Subscribe {

    private int id;
    private float price;
    private Timestamp date;
    private Subscription subscription;
    private int customerID;
    private int subscriptionID;

    public Subscribe() {
    }

    public Subscribe(int id, float price, Timestamp date, Subscription subscription) {
        this.id = id;
        this.price = price;
        this.date = date;
        this.subscription = subscription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getSubscriptionID() {
        return subscriptionID;
    }

    public void setSubscriptionID(int subscriptionID) {
        this.subscriptionID = subscriptionID;
    }
}
