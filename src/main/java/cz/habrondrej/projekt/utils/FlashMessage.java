package cz.habrondrej.projekt.utils;

public class FlashMessage {

    public static final String TYPE_SUCCESS = "success";
    public static final String TYPE_INFO = "info";
    public static final String TYPE_WARNING = "warning";
    public static final String TYPE_DANGER = "danger";

    private String type;
    private String message;

    public FlashMessage() {}

    public FlashMessage(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
