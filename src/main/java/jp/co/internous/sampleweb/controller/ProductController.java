package jp.co.internous.sampleweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sampleweb/product")
public class ProductController {

	@RequestMapping("/")
	public String index() {
		return "product_detail";
	}
}
