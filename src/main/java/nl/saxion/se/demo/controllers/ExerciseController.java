package nl.saxion.se.demo.controllers;

import nl.saxion.se.demo.exceptions.DuplicateExerciseException;
import nl.saxion.se.demo.exceptions.ExerciseNotFoundException;
import nl.saxion.se.demo.models.Exercise;
import nl.saxion.se.demo.models.requestModels.ExerciseRequestModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    DataController dataController = DataController.getInstance();

    @PostMapping(path = "")
    @ResponseBody
    public String createExercise(ExerciseRequestModel exercise, HttpSession session) {
        if (session.getAttribute("username") == null)
            return "401";
        try {
            Exercise newExercise = dataController.addExercise(exercise.toExercise());
            return newExercise.getName();
        } catch (DuplicateExerciseException due) {
            return "Exercise already exists";
        }
    }

    @GetMapping(path = "")
    @ResponseBody
    public String getExercises() {
        return dataController.getExercises().toString();
    }

    @GetMapping(path = "/{exerciseName}")
    @ResponseBody
    public String getExercise(@PathVariable("exerciseName") String exerciseName) {
        return dataController.getExercise(exerciseName).toString();
    }

    @PutMapping(path = "/{exerciseName}")
    @ResponseBody
    public String updateExercise(ExerciseRequestModel exerciseModel, @PathVariable("exerciseName") String exerciseName, HttpSession session) {
        if (session.getAttribute("username") == null)
            return "401";
        Exercise exercise = dataController.getExercise(exerciseName);
        try {
            if (exerciseModel.getDescription() != null)
                dataController.updateExercise(exerciseName, exerciseModel.getDescription());
            if (exerciseModel.getTargets() != null)
                dataController.updateExercise(exerciseName, exerciseModel.getTargets());

        } catch (ExerciseNotFoundException enfe) {
            return "Exercise not found";
        }
        return exercise.toString();
    }
}
