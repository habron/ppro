package cz.habrondrej.projekt.model;

public class Room {

    private int id;
    private int capacity;
    private int roomTypeId;
    private RoomType roomType;

    public Room() {
    }

    public Room(int id, int capacity, RoomType roomType) {
        this.id = id;
        this.capacity = capacity;
        this.roomType = roomType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRoomTypeId() {return roomTypeId;}

    public void setRoomTypeId(int roomTypeId) {this.roomTypeId = roomTypeId;}

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}
