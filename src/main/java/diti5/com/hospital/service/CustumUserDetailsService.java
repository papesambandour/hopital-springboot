package diti5.com.hospital.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import diti5.com.hospital.dao.ServiceDOA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import diti5.com.hospital.dao.UtilisateurDAO;
import diti5.com.hospital.model.Role;
import diti5.com.hospital.model.Utilisateur;
@Service
//ou @Component
public class CustumUserDetailsService implements UserDetailsService{
	@Autowired
	private UtilisateurDAO userDAO ;
	@Autowired
	private ServiceDOA serviceDOA ;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Utilisateur user = userDAO.findByUsername(username);
		if(user != null)
		{
			 User u = new User(user.getUsername(),user.getPassword(),
					 true,true,true,true,getAuthorities(user.getListeRoles()));
			  
			 return u ;
		}
		
		return null;
	}
	
	/*private Collection getAuthorities(List roles) {
		List authorities = new ArrayList();
		for(Object role : roles)
		{
			Role l = (Role)role;
			authorities.add(new SimpleGrantedAuthority(l.getLibelle()));
		}
		return authorities ;
	}*/

	private Collection<? extends GrantedAuthority> getAuthorities(
			Collection<Role> roles) {
		List<GrantedAuthority> authorities
				= new ArrayList<>();
		for (Role role: roles) {
			authorities.add(new SimpleGrantedAuthority(role.getLibelle()));
		}
		return authorities;
	}

}
