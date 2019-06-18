package nl.saxion.se.demo.models;

import java.util.Stack;

public class User {

    // TODO: hash password

    private String username;
    private String password;

    Stack<Set> sets;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        sets = new Stack<>();
    }

    public boolean verifyPassword(String password) {
        if (this.password.equals(password))
            return true;
        else
            return false;
    }

    public String getUsername() {
        return username;
    }

    public Set addSet(Set set) {
        sets.push(set);
        return sets.peek();
    }

    public Stack getSets() {
        return sets;
    }

}
