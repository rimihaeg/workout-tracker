package nl.saxion.se.demo.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Exercise {

    String name;
    String description;

    ArrayList<String> target;

    public Exercise(String name) {
        this.name = name;
    }

    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addTarget(String[] targets) {
        this.target.addAll(Arrays.asList(targets));
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getTarget() {
        return target;
    }
}
