package katachi.spring.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import katachi.spring.sample.form.LoginForm;

/**
 * ログインに関するコントローラ
 * */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	/* ログインページを表示する */
	 @GetMapping
	 public String showLogin(@ModelAttribute LoginForm form) {
		// ログインページへ移動する
		 return "login";
	 }
	 
}
