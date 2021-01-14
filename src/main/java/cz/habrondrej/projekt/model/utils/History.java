package cz.habrondrej.projekt.model.utils;

import cz.habrondrej.projekt.model.Employee;
import cz.habrondrej.projekt.model.User;

import java.sql.Timestamp;

public class History {

    private int id;
    private String text;
    private Timestamp date;
    private User user;
    private int entityID;

    public History() {}

    public History(int id, String text, Timestamp date, User user) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.user = user;
    }

    public History(int id, Timestamp date, User user, int entityID) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.entityID = entityID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }
}
