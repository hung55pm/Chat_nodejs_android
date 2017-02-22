package ngochung.app.Models;

import java.util.Date;

/**
 * Created by NGOCHUNG on 2/15/2017.
 */

public class Message {
    private String room_id;
    private String user_id;
    private String name;
    private String message;
    private Date create_date;

    public Message() {
    }

    public Message(String room_id, String user_id, String name, String message, Date create_date) {
        this.room_id = room_id;
        this.user_id = user_id;
        this.name = name;
        this.message = message;
        this.create_date = create_date;
    }

    public Message(String user_id, String name, String message, Date create_date) {
        this.user_id = user_id;
        this.name = name;
        this.message = message;
        this.create_date = create_date;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
