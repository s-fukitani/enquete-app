package katachi.spring.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/* ログイン中のユーザー名およびログアウトボタンの表示（各ページの上部に表示する） */
@Controller
@RequestMapping("/common")
public class UserLogoutController {
	
	/* ページを表示する */
	@GetMapping
	public String showUserLogout(Model model) {
		return "user_logout";
	}
	
}
