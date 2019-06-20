package nl.saxion.se.demo.models.requestModels;

import nl.saxion.se.demo.models.Exercise;

public class ExerciseRequestModel {

    String name;
    String description;

    String[] targets;

    // TODO: make name required

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getTargets() {
        return targets;
    }

    public void setTargets(String[] targets) {
        this.targets = targets;
    }

    public Exercise toExercise() {
        Exercise exercise = new Exercise(name);
        if (description != null)
            exercise.setDescription(description);
        if (targets != null)
            exercise.addTargets(targets);
        return exercise;
    }
}
