package nl.saxion.se.demo.models;

import java.util.LinkedList;

public class User {
    
    private String username;
    private String password;

    LinkedList<Set> sets;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        sets = new LinkedList<>();
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

    public LinkedList<Set> getSets() {
        return sets;
    }

}
