package cz.habrondrej.projekt.model;

public class RoomType {

    private int id;
    private String name;
    private String description;
    private String operatingRules;

    public RoomType() {
    }

    public RoomType(int id, String name, String description, String operatingRules) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.operatingRules = operatingRules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getOperatingRules() {
        return operatingRules;
    }

    public void setOperatingRules(String operatingRules) {
        this.operatingRules = operatingRules;
    }
}
