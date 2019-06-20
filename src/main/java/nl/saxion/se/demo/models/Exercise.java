package nl.saxion.se.demo.models;

import java.util.ArrayList;
import java.util.Arrays;

public class Exercise {

    String name;
    String description;

    ArrayList<String> targets;

    public Exercise(String name) {
        this.name = name;

        targets = new ArrayList<>();
    }

    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;

        targets = new ArrayList<>();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addTargets(String[] targets) {
        this.targets.addAll(Arrays.asList(targets));
    }

    public void addTarget(String target) {
        this.targets.add(target);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getTargets() {
        return targets;
    }
}
