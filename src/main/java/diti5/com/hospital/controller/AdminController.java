package diti5.com.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value="/admin")
public class AdminController {

	@Autowired
	private BCryptPasswordEncoder bCrypteEncoder;
	@RequestMapping(value="/dashbord")
	//@ResponseBody
	//@RequestMapping(value="/course", method = RequestMethod.GET, produces="application/json")
	public ModelAndView index() {
		//return "admin/dashbord";
		ModelAndView mav = new ModelAndView("admin/dashbord");
		return mav;
	}
}
