package nl.saxion.se.demo.controllers;

import nl.saxion.se.demo.exceptions.DuplicateExerciseException;
import nl.saxion.se.demo.exceptions.ExerciseNotFoundException;
import nl.saxion.se.demo.models.Exercise;
import nl.saxion.se.demo.models.requestModels.ExerciseRequestModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/exercises")
public class ExerciseController {

    static DataController dataController = DataController.getInstance();

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
    public static String getExercises(Model model) {
        model.addAttribute("exercises", dataController.getExercises());
        return "exercises";
    }

    @GetMapping(path = "/{exerciseName}")
    public static String getExercise(@PathVariable("exerciseName") String exerciseName, Model model) {
        Exercise exercise = dataController.getExercise(exerciseName);
        if (exercise == null) {
            model.addAttribute("errorMessage", "Could not find exercise");
            return "404";
        }
        model.addAttribute("exercise", exercise);
        return "exercise";
    }

    @PutMapping(path = "/{exerciseName}")
    @ResponseBody
    public String updateExercise(ExerciseRequestModel exerciseModel, @PathVariable("exerciseName") String exerciseName, HttpSession session) {
        if (session.getAttribute("username") == null)
            return "401";
        Exercise exercise = dataController.getExercise(exerciseName);
        if (exercise == null)
            return "404";
        try {
            if (exerciseModel.getDescription() != null)
                dataController.updateExercise(exerciseName, exerciseModel.getDescription());
            if (exerciseModel.getTargets() != null)
                dataController.updateExercise(exerciseName, exerciseModel.getTargets());

        } catch (ExerciseNotFoundException enfe) {
            return "Exercise not found";
        }

        String retString = exercise.getName();
        if (exercise.getDescription() != null)
            retString += "\t" + exercise.getDescription();
        if (exercise.getTargets().size() > 0)
            retString += "\t" + exercise.getTargets().get(0);
        return retString;
    }
}
