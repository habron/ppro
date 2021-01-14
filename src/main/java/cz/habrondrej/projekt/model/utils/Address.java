package cz.habrondrej.projekt.model.utils;

public class Address {

    private int id;
    private String city;
    private String country;
    private String street;
    private String zip;

    public Address() {
    }

    public Address(int id, String city, String country, String street, String zip) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.street = street;
        this.zip = zip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
