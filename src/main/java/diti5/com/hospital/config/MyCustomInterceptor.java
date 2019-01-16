package diti5.com.hospital.config;

import diti5.com.hospital.dao.UtilisateurDAO;
import diti5.com.hospital.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MyCustomInterceptor implements HandlerInterceptor {
    //unimplemented methods comes here. Define the following method so that it
    //will handle the request before it is passed to the controller.
    public MyCustomInterceptor(UtilisateurDAO userDAO){
        this.userDAO = userDAO ;
    }
    private UtilisateurDAO userDAO ;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal() == "anymousUser")
        {
            return true;
        }
        //Get the username of the logged in user: getPrincipal()
        //Get the password of the authenticated user: getCredentials()
        //Get the assigned roles of the authenticated user: getAuthorities()
        //Get further details of the authenticated user: getDetails()
        Object o = auth.getName();
        String p=o.toString();
        Utilisateur u = userDAO.findByUsername(p);
        if(u.getChanged() == 1)
        {
            return true ;
        }
        response.sendRedirect("/login?changed=1");
        return true;
    }
}
