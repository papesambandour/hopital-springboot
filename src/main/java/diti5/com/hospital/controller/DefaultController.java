package diti5.com.hospital.controller;

import diti5.com.hospital.model.Utilisateur;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value="/default")
public class DefaultController {

    @RequestMapping(value="/error403")
    public String error403() {
        //return "admin/dashbord";
        //ModelAndView view = new ModelAndView("default/error403");
        return "default/error403";
    }
}
