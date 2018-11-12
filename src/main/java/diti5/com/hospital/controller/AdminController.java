package diti5.com.hospital.controller;

import diti5.com.hospital.dao.ServiceDOA;
import diti5.com.hospital.dao.UtilisateurDAO;
import diti5.com.hospital.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
		return view;
	}
	@RequestMapping(value="/users/add")
	public ModelAndView usersAdd(
			@RequestParam(value="nom", required=true) String nom,
			@RequestParam(value="prenom", required=true) String prenom,
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="matricule", required=true) String matricule,
			@RequestParam(value="enabled", required=true) String enabled,
			@RequestParam(value="idRole", required=true) String idRole,
			@RequestParam(value="idService", required=true) String idService,
			@RequestParam(value="password", required=true) String password
	) {
		Utilisateur u = new Utilisateur();
		u.setNom(nom);u.setPassword(password);u.setMatricule(matricule);
		u.setUsername(username);u.setService(serviceDOA.findById(Integer.parseInt(idService)).get());
		ModelAndView view = new ModelAndView("admin/users/add");
		return view;
	}
	@RequestMapping(value="/users/edit/{idUser}",method = RequestMethod.GET)
	public ModelAndView usersEdit(@PathVariable("idUser") String idUser)
	{
		ModelAndView view = new ModelAndView("admin/users/edit");
		Utilisateur user = userDAO.findById(Integer.parseInt(idUser)).get();
		view.addObject("user",user);
		return  view ;
	}
	@RequestMapping(value="/users/save",method = RequestMethod.POST)
	public String usersSave(HttpServletResponse httpResponse) throws IOException {
		httpResponse.sendRedirect("/admin/users?addSuccess=1");
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
