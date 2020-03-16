package kr.ac.ssu.street4.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String hello() {return "Hello, World! I'm YuSeong";}

}
