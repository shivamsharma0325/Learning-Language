package com.launguagelearning.model;

public class User {
    public String getUsername() {
        return _username;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return _password;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    String _username;
    String _password;
    int ID;
    public User(String _username, String _password){
        this._username = _username;
        this._password = _password;
    }
}
