package ngochung.app.Models;

import java.util.Date;

/**
 * Created by MR_HUNG on 2/26/2017.
 */

public class MesRecent {
    private String room_id;
    private String friend_name;
    private String sender_id;
    private String sender_name;
    private String message;
    private Date date;

    public MesRecent(String room_id, String friend_name, String sender_id, String sender_name, String message, Date date) {
        this.room_id = room_id;
        this.friend_name = friend_name;
        this.sender_id = sender_id;
        this.sender_name = sender_name;
        this.message = message;
        this.date = date;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
