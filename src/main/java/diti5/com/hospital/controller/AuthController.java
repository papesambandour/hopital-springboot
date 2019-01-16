package diti5.com.hospital.controller;

import diti5.com.hospital.dao.UtilisateurDAO;
import diti5.com.hospital.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value="/auth")
public class AuthController {
    @Autowired
    private BCryptPasswordEncoder bCrypteEncoder;
    @Autowired
    private UtilisateurDAO userDAO ;
    @RequestMapping(value="/change", method = RequestMethod.POST)
    public ModelAndView index(HttpServletResponse httpResponse,
          @RequestParam(value="password", required=true) String password,
          @RequestParam(value="username", required=true) String username,
          @RequestParam(value="newpassword", required=true) String newpassword) throws IOException {

        String passwordEncrypted = bCrypteEncoder.encode(password);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userNameAuth =auth.getName();
        if(userNameAuth.equals(username))
        {
            Utilisateur u = userDAO.findByUsername(userNameAuth);
            u.setChanged(1);u.setPassword(bCrypteEncoder.encode(newpassword));
            userDAO.save(u);
            if(u.getListeRoles().get(0).getLibelle().equals("ADMIN"))
            {
                httpResponse.sendRedirect("/admin/dashbord");
            }else if(u.getListeRoles().get(0).getLibelle().equals("MEDECIN"))
            {
                httpResponse.sendRedirect("/medecin/dashbord");
            }else if(u.getListeRoles().get(0).getLibelle().equals("SECRETAIRE"))
            {
                httpResponse.sendRedirect("/secretaire/dashbord");
            }


        }
        else {
            httpResponse.sendRedirect("/login?changed=1&changeError=1");
        }

        return  null ;
    }
}
