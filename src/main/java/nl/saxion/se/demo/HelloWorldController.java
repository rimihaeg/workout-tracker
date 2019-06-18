package nl.saxion.se.demo;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    @GetMapping(path = "/world")
    @ResponseBody
    public String getHelloWorld() {
        return "IntelliJ and Gradle make me want to punch a hole through my door.";
    }

}
