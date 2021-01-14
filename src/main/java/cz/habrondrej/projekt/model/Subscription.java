package cz.habrondrej.projekt.model;


public class Subscription {

    private int id;
    private float price;
    private int days;
    private String name;

    public Subscription() {
    }

    public Subscription(int id, float price, int days, String name) {
        this.id = id;
        this.price = price;
        this.days = days;
        this.name = name;
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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
