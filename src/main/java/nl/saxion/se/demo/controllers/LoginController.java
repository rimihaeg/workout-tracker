package nl.saxion.se.demo.controllers;

import nl.saxion.se.demo.models.User;
import nl.saxion.se.demo.models.requestModels.UserRequestModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.security.auth.Subject;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/login")
public class LoginController {

    DataController dataController = DataController.getInstance();

    @PostMapping(path = "")
    public ResponseEntity<Subject> login(UserRequestModel userReq, HttpSession session, Model model) {
        User user = dataController.getUser(userReq.getUsername());
        if (user != null) {
            if (user.verifyPassword(userReq.getPassword())) {
                session.setAttribute("username", user.getUsername());
                session.setAttribute("sets", user.getSets());
                model.addAttribute("username", user.getUsername());

                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", "/me");

                return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
            } else
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

}
