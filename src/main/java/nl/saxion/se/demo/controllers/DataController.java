package nl.saxion.se.demo.controllers;

import nl.saxion.se.demo.exceptions.DuplicateExerciseException;
import nl.saxion.se.demo.exceptions.DuplicateUserException;
import nl.saxion.se.demo.exceptions.ExerciseNotFoundException;
import nl.saxion.se.demo.models.Exercise;
import nl.saxion.se.demo.models.User;
import nl.saxion.se.demo.models.requestModels.ExerciseRequestModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataController {

    Map<String, User> users;
    Map<String, Exercise> exercises;

    private static DataController dataController;

    public static DataController getInstance() {
        if (dataController == null)
            dataController = new DataController();
        return dataController;
    }

    private DataController() {
        users = new HashMap<>();
        exercises = new HashMap<>();
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public User addUser(User user) throws DuplicateUserException{
        if (users.get(user.getUsername()) != null)
            throw new DuplicateUserException();
        users.put(user.getUsername(), user);
        return users.get(user.getUsername());
    }

    public User[] getUsers() {
        return (User[]) users.values().toArray();
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public Exercise getExercise(String exerciseName) {
        return exercises.get(exerciseName);
    }

    public Exercise addExercise(Exercise exercise) throws DuplicateExerciseException {
        if (exercises.get(exercise.getName()) != null)
            throw new DuplicateExerciseException();
        exercises.put(exercise.getName(), exercise);
        return exercises.get(exercise.getName());
    }

    public ArrayList<Exercise> getExercises() {
        return new ArrayList<>(exercises.values());
    }

    public Exercise updateExercise(String exerciseName, String description) throws ExerciseNotFoundException {
        Exercise exercise = exercises.get(exerciseName);
        if (exercise == null)
            throw new ExerciseNotFoundException();
        exercise.setDescription(description);
        return exercise;
    }

    public Exercise updateExercise(String exerciseName, String[] targets) throws ExerciseNotFoundException {
        Exercise exercise = exercises.get(exerciseName);
        if (exercise == null)
            throw new ExerciseNotFoundException();
        exercise.addTarget(targets);
        return exercise;
    }

}
