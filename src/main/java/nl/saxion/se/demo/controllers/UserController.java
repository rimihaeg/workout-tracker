package nl.saxion.se.demo.controllers;

import nl.saxion.se.demo.models.Exercise;
import nl.saxion.se.demo.models.Set;
import nl.saxion.se.demo.models.User;
import nl.saxion.se.demo.models.requestModels.SetRequestModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/me")
public class UserController {

    static DataController dataController = DataController.getInstance();

    @GetMapping(path = "")
    public static String getUser(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null)
            return "401";
        User user = dataController.getUser(username);
        model.addAttribute("username", username);
        model.addAttribute("sets", user.getSets());
        return "me";
    }

    @PostMapping(path = "")
    public String createSet(SetRequestModel set, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null)
            return "401";
        LinkedList<Set> sets = (LinkedList<Set>) session.getAttribute("sets");

        User user = dataController.getUser(username);
        model.addAttribute("username", username);

        Exercise exercise = dataController.getExercise(set.getExerciseName());
        if (exercise == null) {
            model.addAttribute("unknownExercise", "404");
            model.addAttribute("sets", user.getSets());
            return "me";
        }
        model.addAttribute("success", "200");
        sets.push(set.toSet(exercise));
        model.addAttribute("sets", user.getSets());

        return "me";
    }

    @GetMapping("/sets")
    public static String getNumberOfSets(HttpSession session, @RequestParam(value = "limit", required = false, defaultValue = "0") int limit, Model model) {
        if (session.getAttribute("username") == null)
            return "401";

        LinkedList<Set> ph = (LinkedList<Set>) session.getAttribute("sets");
        List<Set> sets;

        if (limit != 0 && limit <= ph.size()) {
            sets = ph.subList(0, limit);
        } else {
            sets = ph.subList(0, ph.size());
        }
        model.addAttribute("sets", sets);

        return "sets";
    }

}
