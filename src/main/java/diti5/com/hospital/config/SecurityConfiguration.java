package diti5.com.hospital.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;


    @Autowired
    private UserDetailsService userDetailsService ;
    /*
    @Value("${spring.queries.utilisateur-query}")
    private String usersQuery;

    @Value("${spring.queries.role-query}")
    private String rolesQuery;
*/
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
        userDetailsService(userDetailsService)
                //jdbcAuthentication()
               // .usersByUsernameQuery(usersQuery)
                //.authoritiesByUsernameQuery(rolesQuery)
                //.dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();
        http.authorizeRequests()
                .antMatchers("/static/**","/*.js","/*.js.map","/*.css").permitAll();
    	http.csrf().disable();
        http.
                authorizeRequests()

                .antMatchers("/login").permitAll()
            //    .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('ADMIN')")//hasRole("ADMIN")
              //  .antMatchers("/medecin/**").hasRole("MEDECIN")
                //.antMatchers("/secretaire/**").hasRole("SECRETAIRE")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .successHandler(myAuthenticationSuccessHandler());
        //https://www.journaldev.com/8748/spring-security-role-based-access-authorization-example
             
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers( "/assets/**","/css/**", "/js/**","/images/**");
    }
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
    	return new MySimpleUrlAuthenticationSuccessHandler();
    }

}