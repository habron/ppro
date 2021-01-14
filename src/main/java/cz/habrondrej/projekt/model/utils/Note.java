package cz.habrondrej.projekt.model.utils;

import cz.habrondrej.projekt.model.Employee;

import java.sql.Timestamp;

public class Note {
    private int id;
    private String text;
    private Employee employee;
    private Timestamp date;
    private int EventID;

    public Note() {}

    public Note(int id, String text, Employee employee, Timestamp date) {
        this.id = id;
        this.text = text;
        this.employee = employee;
        this.date = date;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getEventID() {
        return EventID;
    }

    public void setEventID(int eventID) {
        EventID = eventID;
    }
}
