package nl.saxion.se.demo.models.requestModels;

import nl.saxion.se.demo.models.Exercise;
import nl.saxion.se.demo.models.Set;

public class SetRequestModel {

    String exerciseName;
    int reps;

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Set toSet(Exercise exercise) {
        return new Set(exercise, reps);
    }
}
