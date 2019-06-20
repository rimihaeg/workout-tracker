package nl.saxion.se.demo.controllers;

import nl.saxion.se.demo.exceptions.DuplicateUserException;
import nl.saxion.se.demo.models.User;
import nl.saxion.se.demo.models.requestModels.UserRequestModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("api/users")
public class UsersController {

    static DataController dataController = DataController.getInstance();

    @PostMapping(path = "")
    public String createUser(UserRequestModel user, Model model) {
        try {
            User newUser = dataController.addUser(user.toUser());
            model.addAttribute("username", newUser.getUsername());
            return "login";
        } catch (DuplicateUserException due) {
            model.addAttribute("duplicateUser", user.getUsername());
            return "login";
        }
    }

    @GetMapping(path = "/{username}")
    public static String getUser(@PathVariable("username") String username, HttpSession session, Model model) {
        if (session.getAttribute("username") == null) {
            model.addAttribute("401");
            return "401";
        }
        User user = dataController.getUser(username);
        if (user == null)
            return "404";
        return "user"; // TODO: change to users?
    }

    @GetMapping(path = "")
    public static String getUsers(HttpSession session) {
        if (session.getAttribute("username") == null)
            return "401";
        return "users";
    }

}
