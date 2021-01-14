package cz.habrondrej.projekt.model.utils;

import org.springframework.web.multipart.MultipartFile;

public class Image {
    private int id;
    private byte[] imageData;
    private String type;
    private int userID;
    private String name;
    private MultipartFile file;

    public Image() {
    }

    public Image(int id, String type, int userID, byte[] imageData, String name) {
        this.id = id;
        this.type = type;
        this.userID = userID;
        this.imageData = imageData;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
