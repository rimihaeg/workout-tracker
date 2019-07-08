package nl.saxion.se.demo.controllers;

import nl.saxion.se.demo.exceptions.DuplicateExerciseException;
import nl.saxion.se.demo.exceptions.ExerciseNotFoundException;
import nl.saxion.se.demo.models.Exercise;
import nl.saxion.se.demo.models.requestModels.ExerciseRequestModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    static DataController dataController = DataController.getInstance();

    @PostMapping(path = "")
    public String createExercise(ExerciseRequestModel exercise, Model model, HttpSession session, HttpServletResponse response) {
        if (session.getAttribute("username") == null)
            return "401";
        try {
            Exercise newExercise = dataController.addExercise(exercise.toExercise());
            String encodedCookieValue = URLEncoder.encode(newExercise.getName(), "UTF-8");
            Cookie cookie = new Cookie("lastExerciseAdded", encodedCookieValue);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setPath("/exercises");
            response.addCookie(cookie);
        } catch (DuplicateExerciseException due) {
            model.addAttribute("errorMessage", "The exercise already exists");
            return "404";
        } catch (UnsupportedEncodingException uee) {

        } finally {
            return getExercises(model, session, exercise.getName());
        }
    }

    @GetMapping(path = "")
    public static String getExercises(Model model, HttpSession session, @CookieValue(value = "lastExerciseAdded", defaultValue = "") String lastExerciseAdded) {
        String username = (String) session.getAttribute("username");
        if (username != null)
            model.addAttribute("username", username);
        if (lastExerciseAdded != "")
            model.addAttribute("lastExerciseAdded", lastExerciseAdded);
        System.out.println(dataController.getExercises().size());
        model.addAttribute("exercises", dataController.getExercises());
        return "exercises";
    }

    @GetMapping(path = "/{exerciseName}")
    public static String getExercise(@PathVariable("exerciseName") String exerciseName, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null)
            model.addAttribute("username", username);
        Exercise exercise = dataController.getExercise(exerciseName);
        if (exercise == null) {
            model.addAttribute("errorMessage", "Could not find exercise");
            return "404";
        }
        model.addAttribute("exercise", exercise);
        return "exercise";
    }

    /**
     * Should be a PUT function, but HTML form doesn't support this.
     * @param exerciseModel
     * @param exerciseName
     * @param model
     * @param session
     * @return
     */
    @PostMapping(path = "/{exerciseName}")
    public String updateExercise(ExerciseRequestModel exerciseModel, @PathVariable("exerciseName") String exerciseName, Model model, HttpSession session) {
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
            model.addAttribute("errorMessage", "Could not find exercise");
            return "404";
        }

        return getExercise(exerciseName, model, session);
    }
}
