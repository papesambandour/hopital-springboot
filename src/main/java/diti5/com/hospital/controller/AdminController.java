package diti5.com.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private BCryptPasswordEncoder bCrypteEncoder;
	
	@RequestMapping(value="/index")
	public String index() {
		return "index";
	}
}
