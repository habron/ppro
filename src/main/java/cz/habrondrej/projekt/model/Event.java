package cz.habrondrej.projekt.model;

import cz.habrondrej.projekt.model.utils.History;
import cz.habrondrej.projekt.model.utils.Note;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private int id;
    private Date dateFrom;
    private Date dateTo;
    private int capacity;
    private int roomId;
    private Room room;
    private String name;
    private String description;
    private boolean signedIn;
    private Employee userCreate;
    private List<User> loginUsers = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();
    private List<History> history = new ArrayList<>();

    public Event() {
    }

    public Event(int id, Date dateFrom, Date dateTo, int capacity, int roomId, String name, String description) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.capacity = capacity;
        this.roomId = roomId;
        this.name = name;
        this.description = description;
    }

    public Event(Date dateFrom, Date dateTo, int capacity, String name, String description) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.capacity = capacity;
        this.name = name;
        this.description = description;
    }

    public boolean isUserSigned(User user) {
        for(User loginUser : loginUsers) {
            if (loginUser.getId() == user.getId()) {
                signedIn = true;
                return true;
            } else {
                signedIn = false;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getLoginUsers() {
        return loginUsers;
    }

    public void setLoginUsers(List<User> loginUsers) {
        this.loginUsers = loginUsers;
    }

    public boolean isSignedIn() {
        return signedIn;
    }

    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }

    public Employee getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(Employee userCreate) {
        this.userCreate = userCreate;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

    public int getLoggedUsersCount() {
        return loginUsers.size();
    }
}
