package katachi.spring.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import katachi.spring.sample.utility.SecuritySession;

/* メニューコントローラ */
@Controller
@RequestMapping("/")
public class MenuController {
	
	/* メニューページを表示する */
	@GetMapping
	public String showMenu(HttpSession session) {
		
		// セッションへユーザ名を保存
		SecuritySession securitySession = new SecuritySession();
		String loginUserName = securitySession.getUsername();
		session.setAttribute("loginUserName", loginUserName);
		
		// メニューページへ移動する
		return "menu";
	}
}
