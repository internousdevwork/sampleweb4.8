package jp.co.internous.sampleweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sampleweb/mypage")
public class MyPageController {
	
	@RequestMapping("/")
	public String index() {
		return "my_page";
	}

}
