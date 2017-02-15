package ngochung.app.Models;

import java.util.Date;

/**
 * Created by NGOCHUNG on 2/15/2017.
 */

public class Message {
    private String name;
    private String message;
    private Date create_date;

    public Message(String name, String message, Date create_date) {
        this.name = name;
        this.message = message;
        this.create_date = create_date;
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
