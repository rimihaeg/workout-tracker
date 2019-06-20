package nl.saxion.se.demo.models.requestModels;

import nl.saxion.se.demo.models.User;

public class UserRequestModel {

    String username;
    String password;

    // TODO: make fields required

    public UserRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User toUser() {
        return new User(username, password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
