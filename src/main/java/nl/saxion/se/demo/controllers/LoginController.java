package nl.saxion.se.demo.controllers;

import nl.saxion.se.demo.models.User;
import nl.saxion.se.demo.models.requestModels.UserRequestModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    DataController dataController = DataController.getInstance();

    @PostMapping(path = "")
    @ResponseBody
    public String login(UserRequestModel userReq, HttpSession session) {
        // TODO: session magic
        User user = dataController.getUser(userReq.getUsername());
        if (user != null) {
            if (user.verifyPassword(userReq.getPassword())) {
                session.setAttribute("username", user.getUsername());
                session.setAttribute("sets", user.getSets());
                return session.getId();
            } else
                return "401";
        }
        else
            return "401";

    }
}
