package jp.co.internous.sampleweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sampleweb/user")
public class UserController {
	
	@RequestMapping("/")
	public String index() {
		return "register_user";
	}
}
