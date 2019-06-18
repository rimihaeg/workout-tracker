package nl.saxion.se.demo.controllers;

import nl.saxion.se.demo.models.Exercise;
import nl.saxion.se.demo.models.Set;
import nl.saxion.se.demo.models.requestModels.SetRequestModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Stack;

@Controller
@RequestMapping("/me")
public class UserController {

    DataController dataController = DataController.getInstance();

    @GetMapping(path = "")
    @ResponseBody
    public String getUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null)
            return "401";
        return username;
    }

    @PostMapping(path = "")
    @ResponseBody
    public String createSet(SetRequestModel set, HttpSession session) {
        if (session.getAttribute("username") == null)
            return "401";
        Stack<Set> sets = (Stack<Set>) session.getAttribute("sets");

        Exercise exercise = dataController.getExercise(set.getExerciseName());
        if (exercise == null)
            return "exercise does not exist";
        sets.push(set.toSet(exercise));
        return sets.peek().getExercise().getName();
    }

    @GetMapping("/sets")
    @ResponseBody
    public String getNumberOfSets(HttpSession session, @RequestParam(value = "limit", required = false, defaultValue = "0") int limit) {
        if (session.getAttribute("username") == null)
            return "401";

        Stack<Set> sets = (Stack<Set>) session.getAttribute("sets");

        if (limit != 0) {
            return "" + limit;
        } else {
            return "there's no limit!";
        }


    }

}
