package diti5.com.hospital.config;

import diti5.com.hospital.dao.UtilisateurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private UtilisateurDAO userDAO ;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder;
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("auth/login");
        registry.addViewController("/login").setViewName("auth/login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> paths = new ArrayList<>();
        paths.add("/admin/**");
        paths.add("/medecin/**");
        paths.add("/secretaire/**");
         registry.addInterceptor(new MyCustomInterceptor(userDAO) ).addPathPatterns(paths);
    }
}
