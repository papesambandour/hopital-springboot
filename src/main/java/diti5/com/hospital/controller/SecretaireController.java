package diti5.com.hospital.controller;

import diti5.com.hospital.dao.RoleDAO;
import diti5.com.hospital.dao.ServiceDOA;
import diti5.com.hospital.dao.UtilisateurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/secretaire")
public class SecretaireController {
    @Autowired
    private BCryptPasswordEncoder bCrypteEncoder;
    @Autowired
    private UtilisateurDAO userDAO ;
    @Autowired
    private ServiceDOA serviceDOA;
    @Autowired
    private RoleDAO roleDAO ;
    @RequestMapping(value="/dashbord")
    //@ResponseBody
    //@RequestMapping(value="/course", method = RequestMethod.GET, produces="application/json")
    public ModelAndView index() {
        //return "admin/dashbord";
        ModelAndView view = new ModelAndView("secretaire/dashbord");
        return view;
    }
}
