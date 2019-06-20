package nl.saxion.se.demo.models;

public class Set {

    Exercise exercise;
    int reps;

    public Set(Exercise exercise, int reps) {
        this.exercise = exercise;
        this.reps = reps;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public int getReps() {
        return reps;
    }
}
