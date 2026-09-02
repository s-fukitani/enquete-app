package katachi.spring.sample.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import katachi.spring.sample.entity.Enquete;
import katachi.spring.sample.entity.EnqueteUser;
import katachi.spring.sample.entity.Result;
import katachi.spring.sample.form.EnqueteForm;
import katachi.spring.sample.service.EnqueteService;
import katachi.spring.sample.service.UserService;
import katachi.spring.sample.utility.SecuritySession;
import lombok.RequiredArgsConstructor;

/**
 * アンケート一覧およびアンケート回答に関するコントローラ
 * */
@Controller
@RequestMapping("/enquete")
@RequiredArgsConstructor
public class EnqueteListController {
	
	private final EnqueteService enqueteService;
	private final UserService userService;
	
	/* アンケート一覧ページを表示する */
	@GetMapping
	public String enqueteList(Model model) {
		
		// SecuritySessionよりログイン中のユーザー名を取得
		SecuritySession securitySession = new SecuritySession();
		String loginUserName = securitySession.getUsername();
		
		// ログイン中のユーザー名よりログイン中のユーザーIDの取得
		EnqueteUser enqueteUser = userService.findByUserNameEnqueteUser(loginUserName);
		Integer loginUserId = enqueteUser.getId();
		
		// アンケート情報の取得
		List<Enquete> enquetes = enqueteService.findAllEnquete();
		
		// 表示用アンケートマップの初期化を行う
		Map<Enquete, Boolean> enqueteMap = new LinkedHashMap<>();
		
		for(Enquete enquete : enquetes) {
			// 対象のアンケートをログイン中のユーザーが回答済みかどうかのチェックを行う
			Boolean flag = enqueteService.checkAnsweredEnquete(enquete.getId(), loginUserId);
			// マップにアンケートおよび回答済み情報を追加する
			enqueteMap.put(enquete, flag);
			
		}
		
		// 表示用アンケートマップをアンケート一覧ページに渡す
		model.addAttribute("enqueteMap", enqueteMap);
		
		// アンケート一覧ページへ移動する
		return "enquete/enquete_list";
	}
	
	/* アンケート回答ページを表示する */
	@GetMapping("/{id}")
	public String enquete(@PathVariable Integer id, Model model, RedirectAttributes attributes) {
		
		// 回答対象のアンケートの情報を取得する
		Enquete enquete = enqueteService.findByIdEnquete(id);
		
		// SecuritySessionよりログイン中のユーザー名を取得
		SecuritySession securitySession = new SecuritySession();
		String userName = securitySession.getUsername();
		
		// ログイン中のユーザー名よりログイン中のユーザーIDの取得
		EnqueteUser enqueteUser = userService.findByUserNameEnqueteUser(userName);
		Integer userId = enqueteUser.getId();
		
		// 回答対象アンケート情報およびログイン中のユーザーIDをアンケート回答ページに渡す
		model.addAttribute("userId", userId);
		model.addAttribute("enquete", enquete);
		
		// アンケート回答ページへ移動する
        return "enquete/enquete";
		
	}
	
	/* アンケート回答処理を行う */
	@PostMapping
	public String enqueteSubmit(Model model, EnqueteForm form) {
		
		System.out.println(form.getQuestionId().size());
		
		// アンケート内の問題が終了するまで、1問毎に回答結果を作成し、回答結果テーブルに登録する
		for(int i = 0; i < form.getQuestionId().size(); i++) {
			// 回答結果の初期化
			Result result = new Result();
			
			// 対象アンケート
			result.setEnqueteId(form.getEnqueteId());
			// アンケート内の問題
			result.setQuestionId(form.getQuestionId().get(i));
			// 選択した選択肢
			result.setAnswerId(form.getAnswerMap().
					get(form.getQuestionId().get(i)));
			// ログイン中のユーザー
			result.setUserId(form.getUserId());
			
			// 作成した結果を登録する
			enqueteService.insertEnqueteResult(result);
		}
		
		// アンケート回答時のメッセージをアンケート一覧ページに渡す
		String message = "アンケートの回答が完了しました";
		model.addAttribute("message", message);
		
		// アンケート一覧ページへ移動する
		return this.enqueteList(model);
	}
}
