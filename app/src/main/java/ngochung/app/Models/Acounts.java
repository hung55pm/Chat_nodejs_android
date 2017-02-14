package ngochung.app.Models;

import java.util.Date;

/**
 * Created by NGOCHUNG on 2/14/2017.
 */

public class Acounts {
    private String user_id;
    private String name;
    private String password;
    private Date birthday;
    private int role;
    private String address;
    private String email;
    private String access_token;

    public Acounts(String user_id, String name, String email, String password) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public java.lang.String getUser_id() {
        return user_id;
    }

    public void setUser_id(java.lang.String user_id) {
        this.user_id = user_id;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getPassword() {
        return password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public java.lang.String getAddress() {
        return address;
    }

    public void setAddress(java.lang.String address) {
        this.address = address;
    }

    public java.lang.String getEmail() {
        return email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public java.lang.String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(java.lang.String access_token) {
        this.access_token = access_token;
    }
}
