package jp.co.internous.sampleweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sampleweb/settlement")
public class SettlementController {
	
	@RequestMapping("/")
	public String settlement() {
		return "settlement";
	}

}
