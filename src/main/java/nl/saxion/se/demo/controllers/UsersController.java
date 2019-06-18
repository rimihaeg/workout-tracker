package nl.saxion.se.demo.controllers;

import nl.saxion.se.demo.exceptions.DuplicateUserException;
import nl.saxion.se.demo.models.User;
import nl.saxion.se.demo.models.requestModels.UserRequestModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UsersController {

    DataController dataController = DataController.getInstance();

    @PostMapping(path = "")
    @ResponseBody
    public String createUser(UserRequestModel user) {
        // TODO: return proper user to template
        try {
            User newUser = dataController.addUser(new User(user.getUsername(), user.getPassword()));
            return newUser.getUsername();
        } catch (DuplicateUserException due) {
            return "Username already exists";
        }
    }

    @GetMapping(path = "/{username}")
    @ResponseBody
    public String getUser(@PathVariable("username") String username, HttpSession session) {
        if (session.getAttribute("username") == null)
            return "401";
        User user = dataController.getUser(username);
        if (user == null)
            return "404"; //TODO: return user not found page
        return user.toString();
    }

    @GetMapping(path = "")
    @ResponseBody
    public String getUsers(HttpSession session) {
        if (session.getAttribute("username") == null)
            return "401";
        return dataController.getUsers().toString();
    }

}
