package cz.habrondrej.projekt.model;

import cz.habrondrej.projekt.model.utils.LockerHistory;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Locker {

    private int id;
    private int number;
    private List<LockerHistory> lockerHistory = new ArrayList<>();

    public Locker () {}

    public Locker(int id, int number) {
        this.id = id;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<LockerHistory> getLockerHistory() {
        return lockerHistory;
    }

    public void setLockerHistory(List<LockerHistory> lockerHistory) {
        this.lockerHistory = lockerHistory;
    }

    public boolean getFree() {

        Date date;
        for (LockerHistory history: lockerHistory) {

            date = new Date(System.currentTimeMillis() - history.getTimeTo() * 3600000);
            if (date.before(history.getDate())) {
                return false;
            }
        }
        return true;
    }
}
