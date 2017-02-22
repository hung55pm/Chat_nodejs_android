package ngochung.app.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NGOCHUNG on 2/22/2017.
 */

public class Room {
    private String room_id;
    private List<Message> list;

    public Room(String room_id, List<Message> list) {
        this.room_id = room_id;
        this.list = list;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public List<Message> getList() {
        return list;
    }

    public void setList(ArrayList<Message> list) {
        this.list = list;
    }
}
