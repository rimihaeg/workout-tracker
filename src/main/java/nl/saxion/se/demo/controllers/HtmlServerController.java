package nl.saxion.se.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class HtmlServerController {

    @GetMapping(path = "")
    public String home(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null)
            return "login";
        else
            return "me";
    }

    @Controller
    @RequestMapping("/users")
    public class UsersHtmlController {
        @GetMapping(path = "")
        public String getUsers(HttpSession session) {
            return UsersController.getUsers(session);
        }

        @GetMapping(path = "/{username}")
        public String getUser(@PathVariable("username") String username, HttpSession session, Model model) {
            return UsersController.getUser(username, session, model);
        }
    }

    @Controller
    @RequestMapping("/exercises")
    public class ExercisesHtmlController {
        @GetMapping(path = "")
        public String getExercises(Model model) {
            return ExerciseController.getExercises(model);
        }

        @GetMapping(path = "/{exerciseName}")
        public String getExercise(@PathVariable("exerciseName") String exerciseName, Model model) {
            return ExerciseController.getExercise(exerciseName, model);
        }
    }

    @Controller
    @RequestMapping("/me")
    public class UserHtmlController {
        @GetMapping(path = "")
        public String getUser(HttpSession session, Model model) {
            return UserController.getUser(session, model);
        }
        @GetMapping(path = "/sets")
        public String getSets(HttpSession session, @RequestParam(value = "limit", required = false, defaultValue = "0") int limit, Model model) {
            return UserController.getNumberOfSets(session, limit, model);
        }
    }


}
