package diti5.com.hospital.controller;

import diti5.com.hospital.dao.RoleDAO;
import diti5.com.hospital.dao.ServiceDOA;
import diti5.com.hospital.dao.UtilisateurDAO;
import diti5.com.hospital.model.Role;
import diti5.com.hospital.model.Service;
import diti5.com.hospital.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.FileStore;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Controller
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
	private String IMG_USER = "C:\\Users\\papes\\Documents\\PAPE NDOUR\\DITI5\\JEE\\workspace intelij\\hopital\\src\\main\\resources\\static\\upload\\images\\";
	private String IMG_DELETED = "C:\\Users\\papes\\Documents\\PAPE NDOUR\\DITI5\\JEE\\workspace intelij\\hopital\\src\\main\\resources\\static\\deleted\\images\\";
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
	public ModelAndView usersAdd() {
		ModelAndView view = new ModelAndView("admin/users/add");
		List<Role> roles = roleDAO.findAll();
		List<Service> services = serviceDOA.findAll();
        Utilisateur user = new Utilisateur();
        user.setListeRoles(new ArrayList<Role>());
        user.setService(new Service());
		view.addObject("roles",roles);
		view.addObject("services",services);
		view.addObject("user",user);
		return view;
	}
	@RequestMapping(value="/users/edit/{idUser}",method = RequestMethod.GET)
	public ModelAndView usersEdit(@PathVariable("idUser") String idUser) throws IOException {
		ModelAndView view = new ModelAndView("admin/users/edit");
		Utilisateur user = userDAO.findById(Integer.parseInt(idUser)).get();
		List<Role> roles = roleDAO.findAll();
		List<Service> services = serviceDOA.findAll();
		view.addObject("roles",roles);
		view.addObject("services",services);
		view.addObject("user",user);
		List<Integer> listEtat = new ArrayList<Integer>();
		listEtat.add(0);listEtat.add(1);
		Path path = Paths.get(IMG_USER + user.getImg());
		view.addObject("listEtat",listEtat);
		view.addObject("urlImg",user.getImg());
		return  view ;
	}
	@RequestMapping(value="/users/delete/{idUser}",method = RequestMethod.GET)
	public ModelAndView usersDelete(HttpServletResponse httpResponse,@PathVariable("idUser") String idUser) throws IOException {
		Utilisateur user = userDAO.findById(Integer.parseInt(idUser)).get();
        try {
            Path actualPath = Paths.get(IMG_USER + user.getImg());
            Path deletedPath = Paths.get(IMG_DELETED + user.getImg());
            Files.move(actualPath,deletedPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
		userDAO.delete(user);
		httpResponse.sendRedirect("/admin/users?deleteSuccesse=1");
		return null;
	}
	@RequestMapping(value="/users/save",method = RequestMethod.POST)
	public String usersSave(HttpServletResponse httpResponse,Utilisateur user,
                            @RequestParam("file") MultipartFile file) throws IOException
	{
        user.setUsername(user.getMatricule()+user.getNom());
		Utilisateur uVerify = userDAO.findByMatriculeOrUsername(user.getMatricule(),user.getUsername());
		String img_uri = "";
		if(uVerify != null)
        {
            httpResponse.sendRedirect("/admin/users/add?error=1");
            return  null;
        }
        if(!file.isEmpty())
        {
            try {

                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                img_uri = getUniqId()+file.getOriginalFilename().split("\\.")[1];//file.getOriginalFilename();
                Path path = Paths.get(IMG_USER + img_uri);
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            byte[] bytes = file.getBytes();
            img_uri = getUniqId()+".png";//file.getOriginalFilename();
            Path path = Paths.get(IMG_USER + "userdefault.png");
            Path pathCopy = Paths.get(IMG_USER + img_uri);
            Files.copy(path, pathCopy);
        }
		user.setPassword(bCrypteEncoder.encode("passer"));
		user.setImg(img_uri);user.setChanged(0);
		user.setEnabled(1);
		userDAO.save(user);
		httpResponse.sendRedirect("/admin/users?addSuccess=1");
		return null;

	}
	@RequestMapping(value="/users/update",method = RequestMethod.POST)
	public String usersUpdate(HttpServletResponse httpResponse,
			@RequestParam(value="file") MultipartFile file,
        @RequestParam(value = "urlImg") String urlImg,
        Utilisateur user) throws IOException
	{
        String new_img_uri = "";
       if(!file.isEmpty())
       {
           try {
               byte[] bytes = file.getBytes();
               new_img_uri = getUniqId()+file.getOriginalFilename().split("\\.")[1];
               Path actualPath = Paths.get(IMG_USER + urlImg);
               Path deletedPath = Paths.get(IMG_DELETED + urlImg);
               Path newPath = Paths.get(IMG_USER + new_img_uri);
               Files.write(newPath, bytes);
               Files.move(actualPath,deletedPath);

           } catch (IOException e) {
               e.printStackTrace();
           }
           user.setImg(new_img_uri);
       }
       else {
           user.setImg(urlImg);
       }
		userDAO.save(user);
       System.out.println(user);
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

	public static String getUniqId()
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String requiredDate = df.format(new Date()).toString();
        return  requiredDate.replaceAll("[^0-9]","")+".";
    }
}
