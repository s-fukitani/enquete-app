package katachi.spring.sample.controller;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import katachi.spring.sample.entity.EnqueteUser;
import katachi.spring.sample.entity.Role;
import katachi.spring.sample.form.MakeUserForm;
import katachi.spring.sample.form.UpdateUserForm;
import katachi.spring.sample.service.EnqueteService;
import katachi.spring.sample.service.UserService;
import katachi.spring.sample.utility.SecuritySession;
import lombok.RequiredArgsConstructor;

/* ユーザー管理コントローラ */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final EnqueteService enqueteService;
	private final UserService userService;
	
	/* ユーザー管理メニューページを表示する */
	@GetMapping
	public String showUserMenu() {
		// ユーザー管理メニューページへ移動する
		return "user/user_menu";
	}
	
	/* ユーザー作成ページを表示する */
	@GetMapping("/make")
	public String makeUser(@ModelAttribute MakeUserForm form, Model model) {
		
		// ユーザー作成フォームの作成（ユーザー作成ページにデータを送るために必要）
		model.addAttribute("makeUserForm", new MakeUserForm());
		// 権限情報をユーザー作成ページ（ドロップダウンリスト用）に渡す
		model.addAttribute("roleList", Role.values());
		
		// ユーザー作成ページに移動する
		return "user/user_make";
	}
	
	/* 作成したユーザーを登録する */
	@PostMapping("/make")
	public String makeUserInsert(@Valid @ModelAttribute MakeUserForm form, 
								BindingResult bindingResult, RedirectAttributes attributes, 
								Model model) {
		
		// 権限情報をユーザー作成ページ（ドロップダウンリスト用）に渡す（エラーが発生したときのため）
		model.addAttribute("roleList", Role.values());
		
		// パスワードと確認用パスワードが一致しているかのチェックを行う
		boolean isSamePassword = form.isSamePassword();
		// すでに同じユーザー名が登録されていないかのチェックを行う
		boolean isSameUserName = userService.checkSameUserName(form.getUserName());
		
		if(isSameUserName == true) {
			// すでに同じユーザー名が登録されている場合はエラーの追加を行う
			bindingResult.addError(new FieldError("makeUserForm", "userName", "すでに同じユーザー名が存在します"));
		}
		
		if(bindingResult.hasErrors()) {
			// エラーが存在しているときはページ移動せずにエラーメッセージを表示する
			return "user/user_make";
		}
		
		//エラーが存在しないとき
		if(isSamePassword == true && isSameUserName == false) {
			// 「BCrypt」のインスタンス化
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			// パスワードをハッシュ化
			String encodedPassword = encoder.encode(form.getPassword());
			// ユーザー情報を登録する
			userService.insertUserRecord(form.getUserName(), encodedPassword, form.getAuthority());
		}
		
		// ユーザー登録成功時のメッセージをユーザー一覧ページに渡す
		String message = "新しいユーザー「" + form.getUserName() + "」が登録されました";
		attributes.addFlashAttribute("message", message);
		
		// ユーザー一覧ページにリダイレクトする
		return "redirect:/user/list";
	}
	
	/* ユーザー一覧ページを表示する */
	@GetMapping("/list")
	public String showUsersList(Model model) {
		
		// セッションからログインユーザー名を取得する
		SecuritySession securitySession = new SecuritySession();
		String loginUserName = securitySession.getUsername();
		//取得したユーザー名からユーザーIDを取得する
		EnqueteUser enqueteUser = userService.findByUserNameEnqueteUser(loginUserName);
		Integer loginUserId = enqueteUser.getId();
		
		// ログインユーザーIDと登録ユーザー情報をユーザー一覧ページに渡す
		//（ログインユーザーIDはログイン中のユーザー情報を更新および削除できなくするために渡す）
		model.addAttribute("loginUserId", loginUserId);
		model.addAttribute("users", userService.findAllUser());
		
		// ユーザー一覧ページに移動する
		return "user/user_list";
	}
	
	/* ユーザー更新ページを表示する */
	@PostMapping("/update")
	public String updateUserSubmit(@RequestParam("userId") Integer id,
			@ModelAttribute UpdateUserForm form, Model model) {
		// 選択したユーザー情報を取得する
		EnqueteUser enqueteUser = userService.findByIdEnqueteUser(id);
		// 取得したユーザー情報をフォームに設定する
		form.setUserId(id);
		form.setUserName(enqueteUser.getUserName());
		form.setAuthority(enqueteUser.getAuthority());
		
		// 更新対象のユーザー情報と権限情報（ドロップダウンリスト用）をユーザー更新ページに渡す
		model.addAttribute("updateUserForm", form);
		model.addAttribute("roleList", Role.values());
		
		// ユーザー更新ページに移動する
		return "user/user_update";
	}
	
	/* ユーザーを更新する */
	@PostMapping("/update/confirm")
	public String updateUserConfirmSubmit(@Valid @ModelAttribute UpdateUserForm form, 
			BindingResult bindingResult, 
			RedirectAttributes attributes, Model model) {
		
		// 権限情報をユーザー更新ページ（ドロップダウンリスト用）に渡す（エラーが発生したときのため）
		model.addAttribute("roleList", Role.values());
		
		//パスワードと確認用パスワードが一致しているかのチェックを行う
		boolean isSamePassword = form.isSamePassword();
		
		if(bindingResult.hasErrors()) {
			// エラーが存在しているときはページ移動せずにエラーメッセージを表示する
			return "user/user_update";
		}
		
		//エラーが存在しないとき
		if(isSamePassword == true) {
			// 「BCrypt」のインスタンス化
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			// パスワードをハッシュ化
			String encodedPassword = encoder.encode(form.getPassword());
			// ユーザー情報を更新する
			userService.updateUserRecord(form.getUserId(), form.getUserName(), encodedPassword, form.getAuthority());
		}
		
		// ユーザー更新成功時のメッセージをユーザー一覧ページに渡す
		String message = "ユーザーの更新が完了しました";
		attributes.addFlashAttribute("message", message);
		
		// ユーザー一覧ページにリダイレクトする 
		return "redirect:/user/list";
	}
	
	/* ユーザー削除（確認）ページを表示する */
	@PostMapping("/delete")
	public String deleteUserSubmit(@RequestParam("userId") Integer id, 
			Model model, RedirectAttributes attributes) {
		
		// 削除対象のユーザー情報を取得する
		EnqueteUser enqueteUser = userService.findByIdEnqueteUser(id);
		// 削除対象のユーザー情報をユーザー削除（確認）ページへ渡す
		model.addAttribute("enqueteUser", enqueteUser);
		
		// ユーザー削除（確認）ページに移動する
		return "user/user_delete_confirm";
	}
	
	/* ユーザーを削除する */
	@PostMapping("/delete/confirm")
	public String deleteUserConfirmSubmit(@RequestParam("userId") Integer id, 
			Model model, RedirectAttributes attributes) {
		
		// 削除対象のユーザー情報を取得する（削除完了メッセージにユーザー名を使用するため）
		EnqueteUser enqueteUser = userService.findByIdEnqueteUser(id);
		
		// 削除対象のユーザーとそのユーザーが回答したアンケートの回答結果を削除する
		userService.deleteByIdEnqueteUser(id);
		enqueteService.deleteByUserIdResult(id);
		
		// ユーザー削除成功時のメッセージをユーザー一覧ページに渡す
		String message = "ユーザー「" + enqueteUser.getUserName() + "」が削除されました";
		attributes.addFlashAttribute("message", message);
		
		// ユーザー一覧ページに移動する
		return "redirect:/user/list";
	}
	
}