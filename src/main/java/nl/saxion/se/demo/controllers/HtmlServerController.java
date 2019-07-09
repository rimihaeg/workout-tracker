package nl.saxion.se.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

// TODO: error mapping

@Controller
@RequestMapping("")
public class HtmlServerController {

    @GetMapping(path = "")
    public static String home(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null)
            return "login";
        else
            return UserController.getUser(session, model);
    }

//    @Controller
//    @RequestMapping("/users")
//    public static class UsersHtmlController {
//        @GetMapping(path = "")
//        public static String getUsers(HttpSession session, Model model) {
//            return UsersController.getUsers(session, model);
//        }
//
//        @GetMapping(path = "/{username}")
//        public static String getUser(@PathVariable("username") String username, HttpSession session, Model model) {
//            return UsersController.getUser(username, session, model);
//        }
//    }

//    @Controller
//    @RequestMapping("/exercises")
//    public class ExercisesHtmlController {
//        @GetMapping(path = "")
//        public String getExercises(Model model, HttpSession session, @CookieValue(value = "lastExerciseAdded", required = false) String lastExerciseAdded) {
//            return ExerciseController.getExercises(model, session, lastExerciseAdded);
//        }
//
//        @GetMapping(path = "/{exerciseName}")
//        public String getExercise(@PathVariable("exerciseName") String exerciseName, Model model, HttpSession session) {
//            return ExerciseController.getExercise(exerciseName, model, session);
//        }
//    }

//    @Controller
//    @RequestMapping("/me")
//    public static class UserHtmlController {
//        @GetMapping(path = "")
//        public static String getUser(HttpSession session, Model model) {
//            return UserController.getUser(session, model);
//        }
//        @GetMapping(path = "/sets")
//        public static String getSets(HttpSession session, @RequestParam(value = "limit", required = false, defaultValue = "0") int limit, Model model) {
//            return UserController.getNumberOfSets(session, limit, model);
//        }
//    }

}
