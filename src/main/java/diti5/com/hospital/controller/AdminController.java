package diti5.com.hospital.controller;

import diti5.com.hospital.dao.RoleDAO;
import diti5.com.hospital.dao.ServiceDOA;
import diti5.com.hospital.dao.UtilisateurDAO;
import diti5.com.hospital.model.Role;
import diti5.com.hospital.model.Service;
import diti5.com.hospital.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/admin")
public class AdminController {

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
		ModelAndView view = new ModelAndView("admin/dashbord");
		return view;
	}
	@RequestMapping(value="/users")
	public ModelAndView users() {
		//return "admin/dashbord";
		ModelAndView view = new ModelAndView("admin/users/users");
		List<Utilisateur> users = userDAO.findAll();
		view.addObject("users",users);
		int a=90;
		return view;
	}
	@RequestMapping(value="/users/add")
	public ModelAndView usersAdd() {
		ModelAndView view = new ModelAndView("admin/users/add");
		List<Role> roles = roleDAO.findAll();
		List<Service> services = serviceDOA.findAll();
		view.addObject("roles",roles);
		view.addObject("services",services);
		return view;
	}
	@RequestMapping(value="/users/edit/{idUser}",method = RequestMethod.GET)
	public ModelAndView usersEdit(@PathVariable("idUser") String idUser)
	{
		ModelAndView view = new ModelAndView("admin/users/edit");
		Utilisateur user = userDAO.findById(Integer.parseInt(idUser)).get();
		List<Role> roles = roleDAO.findAll();
		List<Service> services = serviceDOA.findAll();
		view.addObject("roles",roles);
		view.addObject("services",services);
		view.addObject("user",user);
		return  view ;
	}
	@RequestMapping(value="/users/delete/{idUser}",method = RequestMethod.GET)
	public ModelAndView usersDelete(HttpServletResponse httpResponse,@PathVariable("idUser") String idUser) throws IOException {
		Utilisateur user = userDAO.findById(Integer.parseInt(idUser)).get();
		userDAO.delete(user);
		httpResponse.sendRedirect("/admin/users?deleteSuccesse=1");
		return null;
	}
	@RequestMapping(value="/users/save",method = RequestMethod.POST)
	public String usersSave(HttpServletResponse httpResponse,
			@RequestParam(value="nom", required=true) String nom,
			@RequestParam(value="prenom", required=true) String prenom,
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="matricule", required=true) String matricule,
			@RequestParam(value="enabled", required=true) String enabled,
			@RequestParam(value="idRole", required=true) List<String> idRole,
			@RequestParam(value="idService", required=true) String idService,
			@RequestParam(value="password", required=true) String password) throws IOException
	{
		Utilisateur uVerify = userDAO.findByMatriculeOrUsername(matricule,username);
		if(uVerify != null)
        {
            httpResponse.sendRedirect("/admin/users/add?error=1");
            return  null;
        }
		Utilisateur u = new Utilisateur();
		u.setNom(nom);u.setPassword(bCrypteEncoder.encode(password));u.setMatricule(matricule);
		u.setPrenom(prenom);u.setEnabled(Integer.parseInt(enabled));
		List<Role> roles = new ArrayList<Role>();
		for(String idR : idRole)
		{
			roles.add(roleDAO.findById(Integer.parseInt(idR)).get());
		}
		u.setUsername(username);u.setService(serviceDOA.findById(Integer.parseInt(idService)).get());
		u.setListeRoles(roles);
		userDAO.save(u);
		httpResponse.sendRedirect("/admin/users?addSuccess=1");
		return null;

	}@RequestMapping(value="/users/update",method = RequestMethod.POST)
	public String usersUpdate(HttpServletResponse httpResponse,
			@RequestParam(value="id", required=true) String id,
			@RequestParam(value="nom", required=true) String nom,
			@RequestParam(value="prenom", required=true) String prenom,
			@RequestParam(value="enabled", required=true) String enabled,
			@RequestParam(value="listeRoles", required=true) List<String> idRole,
			@RequestParam(value="service", required=true) String idService) throws IOException
	{
		Utilisateur u = userDAO.findById(Integer.parseInt(id)).get();
		u.setNom(nom);/*u.setMatricule(matricule);*/
		u.setPrenom(prenom);u.setEnabled(Integer.parseInt(enabled));
		List<Role> roles = new ArrayList<Role>();
		for(String idR : idRole)
		{
			roles.add(roleDAO.findById(Integer.parseInt(idR)).get());
		}
		/*u.setUsername(username);*/u.setService(serviceDOA.findById(Integer.parseInt(idService)).get());
		u.setListeRoles(roles);
		userDAO.save(u);
		httpResponse.sendRedirect("/admin/users?updateSuccess=1");
		return null;
	}
	@RequestMapping(value="/roles")
	public ModelAndView roles() {
		//return "admin/dashbord";
		ModelAndView view = new ModelAndView("admin/roles/roles");
		return view;
	}
	@RequestMapping(value="/consultations")
	public ModelAndView consultations() {
		//return "admin/dashbord";
		ModelAndView view = new ModelAndView("admin/consultations/consultations");
		return view;
	}
	@RequestMapping(value="/services")
	public ModelAndView services() {
		//return "admin/dashbord";
		ModelAndView view = new ModelAndView("admin/services/services");
		return view;
	}
	@RequestMapping(value="/patients")
	public ModelAndView patients() {
		//return "admin/dashbord";
		ModelAndView view = new ModelAndView("admin/patients/patients");
		return view;
	}@RequestMapping(value="/profil")
	public ModelAndView profil() {
		//return "admin/dashbord";
		ModelAndView view = new ModelAndView("admin/profil/profil");
		return view;
	}
}
