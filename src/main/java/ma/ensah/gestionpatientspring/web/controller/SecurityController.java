package ma.ensah.gestionpatientspring.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {


    @GetMapping(path = "/403")
    public String NotAllowed(){
        return "403";
    }
}
