package life.majiang.community1.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller

public class helloController {
    @GetMapping("/hello")
    public String hello(@RequestParam(name="name") String name,Model model) {
        model.addAttribute("name",name);
        return "hello";

    }
}

