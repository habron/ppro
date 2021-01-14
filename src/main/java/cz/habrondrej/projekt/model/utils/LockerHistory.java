package cz.habrondrej.projekt.model.utils;

import cz.habrondrej.projekt.model.User;

import java.sql.Timestamp;

public class LockerHistory extends History {

    private int timeTo;
    private int userID;

    public LockerHistory() {}

    public LockerHistory(int id, Timestamp date, User user, int timeTo, int entityID) {
        super(id, date, user, entityID);
        this.timeTo = timeTo;
    }

    public int getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(int timeTo) {
        this.timeTo = timeTo;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

}
