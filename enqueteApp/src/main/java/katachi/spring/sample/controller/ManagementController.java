package katachi.spring.sample.controller;

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
import katachi.spring.sample.entity.Enquete;
import katachi.spring.sample.entity.Question;
import katachi.spring.sample.form.EnqueteDetailForm;
import katachi.spring.sample.form.EnqueteOutlineForm;
import katachi.spring.sample.service.EnqueteService;
import lombok.RequiredArgsConstructor;

/**
 * ユーザー情報およびアンケート情報の管理を行うコントローラ
 * */
@Controller
@RequestMapping("/management")
@RequiredArgsConstructor
public class ManagementController {
	
	private final EnqueteService enqueteService;
	
	/* 管理メニューページを表示する */
	@GetMapping
	public String managementMenu(Model model) {
		
		// 管理メニューページへ移動する
		return "management/management_menu";
	}
	
	/* アンケート作成（概略）ページを表示する */
	@GetMapping("/make")
	public String makeEnqueteOutline(Model model, EnqueteOutlineForm form) {
		// 以下の情報をアンケート作成（概略）ページに渡す
		model.addAttribute("enqueteTitle", form.getEnqueteTitle());	//アンケートタイトル
		model.addAttribute("questionMax", form.getQuestionMax());	//問題数
		model.addAttribute("answerMax", form.getAnswerMax());		//1問毎の選択肢数
		
		// アンケート作成（概略）ページへ移動する
		return "management/make_enquete_outline";
	}
	
	/* アンケート作成（詳細）ページを表示する */
	@PostMapping("/make")
	public String makeEnqueteDetail(Model model,
			@Valid @ModelAttribute EnqueteOutlineForm form, 
			BindingResult bindingResult) {
		
		// 以下の情報をアンケート作成（詳細）ページに渡す
		model.addAttribute("enqueteDetailForm", new EnqueteDetailForm());	//アンケート作成（詳細）フォームの作成（詳細ページにデータを送るために必要）
		model.addAttribute("enqueteTitle", form.getEnqueteTitle());			//アンケートタイトル
		model.addAttribute("questionMax", form.getQuestionMax());			//問題数
		model.addAttribute("answerMax", form.getAnswerMax());				//1問毎の選択肢数
		
		//既に同じアンケートタイトルが登録されているかのチェックを行う
		boolean isSameEnqueteTitle = enqueteService.checkEnqueteSameTitle(form.getEnqueteTitle());
		
		if(isSameEnqueteTitle == true) {
			// すでに同じアンケートタイトルが登録されている場合はエラーの追加を行う
			bindingResult.addError(new FieldError("EnqueteOutlineForm", "enqueteTitle", "すでに同じアンケートタイトルが存在します"));
		}
		
		if(bindingResult.hasErrors()) {
			// エラーが存在しているときはページ移動せずにエラーメッセージを表示する
			return "management/make_enquete_outline";
		}
		
		// エラーが存在しないときはアンケート作成（詳細）へ移動する
		return "management/make_enquete_detail";
	}
	
	/* アンケート作成（確認）ページからアンケート作成（詳細）ページに戻る */
	@PostMapping("/make/detail")
	public String makeEnqueteReturnDetail(Model model, @ModelAttribute EnqueteDetailForm form) {
		
		// 以下の情報をアンケート作成（詳細）ページに渡す
		model.addAttribute("enqueteTitle", form.getEnqueteTitle());	//アンケートタイトル
		model.addAttribute("questions", form.getQuestions());		//全質問
		model.addAttribute("answers", form.getAnswers());			//全選択肢
		model.addAttribute("questionMax", form.getQuestionMax());	//質問数
		model.addAttribute("answerMax", form.getAnswerMax());		//1問毎の選択肢数
		
		//アンケート作成（詳細）ページに移動する
		return "management/make_enquete_detail";
	}
	
	/* アンケート作成（確認）ページを表示する */
	@PostMapping("/make/confirm")
	public String makeEnqueteSubmit(Model model,
			@Valid @ModelAttribute EnqueteDetailForm form, 
			BindingResult bindingResult) {
		
		// 以下の情報をアンケート作成（確認）ページに渡す
		model.addAttribute("enqueteTitle", form.getEnqueteTitle());	//アンケートタイトル
		model.addAttribute("questions", form.getQuestions());		//全質問
		model.addAttribute("answers", form.getAnswers());			//全選択肢
		model.addAttribute("questionMax", form.getQuestionMax());	//質問数
		model.addAttribute("answerMax", form.getAnswerMax());		//1問毎の選択肢数
		
		// エラーメッセージ表示のための変数
		int q_num = 0;	//質問番号
		int a_num = 0;	//選択肢番号
		String q_arr;	//質問配列
		String a_arr;	//選択肢配列
		String q_mes;	//質問に関するメッセージ
		String a_mes;	//選択肢に関するメッセージ
		
		// エラーチェックのための質問及び選択肢の取得
		String[] questions = form.getQuestions();
		String[][] answers = form.getAnswers();
		
		// エラーチェックのためのフラグの初期化
		boolean checkQuestion = false;								//質問
		
		boolean[] checkAnswer = new boolean[form.getQuestionMax()];	//選択肢
		for(int i = 0; i < form.getQuestionMax(); i++) {
			checkAnswer[i] = false;
		}
		
		// 未入力時のエラーの追加及びエラーメッセージの作成
		for(int i = 0; i < form.getQuestionMax(); i++) {
			// 質問
			if(questions[i].isBlank()) {
				q_arr = "questions[" + i + "]";
				q_num = i + 1;
				q_mes = q_num + "番目の質問が入力されていません";
				bindingResult.addError(new FieldError("enqueteDetailForm", q_arr, q_mes));
			}
			
			// 選択肢
			for(int j = 0; j < form.getAnswerMax(); j++) {
				if(answers[i][j].isBlank()) {
					a_arr = "answers[" + i + "][" + j + "]";
					a_num = j + 1;
					a_mes = a_num + "番目の選択肢が入力されていません";
					bindingResult.addError(new FieldError("enqueteDetailForm", a_arr, a_mes));
				}
			}
		}
		
		// 同じ質問が存在するときはフラグをONにする
		for(int i = 0; i < form.getQuestionMax() - 1; i++) {
			for(int j = i + 1; j < form.getQuestionMax(); j++) {
				if((!questions[i].isBlank()) && (!questions[j].isBlank())) {
					if(questions[i].equals(questions[j])) {
						checkQuestion = true;
					}
				}
			}
		}
		
		// 1つの質問内に同じ選択肢が存在するときはフラグをONにする
		for(int i = 0; i < form.getQuestionMax(); i++) {
			for(int j = 0; j < form.getAnswerMax() - 1; j++) {
				for(int k = j + 1; k < form.getAnswerMax(); k++) {
					if((!answers[i][j].isBlank()) && (!answers[i][k].isBlank())) {
						if(answers[i][j].equals(answers[i][k])) {
							checkAnswer[i] = true;
						}
					}
				}
			}
		}
		
		if(checkQuestion == true) {
			// 同じ質問が存在している場合はエラーの追加を行う
			bindingResult.addError(new FieldError("enqueteDetailForm", "enqueteTitle", "重複している問題があります"));
		}
		
		
		for(int i = 0; i < form.getQuestionMax(); i++) {
			if(checkAnswer[i] == true) {
				// 1つの質問内に同じ選択肢が存在している場合はエラーの追加を行う
				String arr = "questions[" + i + "]";
				String mes = "問";
				int num = i + 1;
				mes = mes + num + "に重複している選択肢があります";
				bindingResult.addError(new FieldError("enqueteDetailForm", arr, mes));
			}
		}
		
		//
		if(bindingResult.hasErrors()) {
			// エラーが存在しているときはページ移動せずにエラーメッセージを表示する
			return "management/make_enquete_detail";
		}
		
		// エラーが存在しないときはアンケート作成（確認）ページへ移動する
		return "management/make_enquete_confirm";
	}
	
	
	/* 作成したアンケートを登録する */
	@PostMapping
	public String makeEnqueteConfirm(Model model, EnqueteDetailForm form) {
		
		//アンケートをアンケートテーブルへ登録
		enqueteService.insertEnqueteRecord(form.getEnqueteTitle());
		
		// 登録したアンケートからアンケートIDを取得
		Integer enquete_id = enqueteService.findByTitleEnqueteId(form.getEnqueteTitle());
		//System.out.println(enquete_id);
		
		// フォームの問題と選択肢を配列へ代入
		String[] strQuestion = form.getQuestions();
		String[][] strAnswer = form.getAnswers();

		for(int i = 0; i < strAnswer.length; i++) {
			// 問題を問題テーブルへ登録
			enqueteService.insertQuestionRecord(enquete_id, strQuestion[i]);
			Integer question_id = enqueteService.findByTextQuestionId(strQuestion[i], enquete_id);
			for(int j = 0; j < strAnswer[i].length; j++) {
				// 選択肢を選択肢テーブルへ登録
				enqueteService.insertAnswerRecord(question_id, strAnswer[i][j]);
			}
		}
		
		// アンケート作成成功時のメッセージを渡す
		String message = "新しいアンケートが作成されました";
		model.addAttribute("message", message);
		
		// 管理メニューページへ移動する
		return "management/management_menu";
	}
	
	/* アンケート削除（一覧）ページを表示する */
	@GetMapping("/delete")
	public String deleteEnquete(Model model) {
		
		// アンケート情報をアンケート削除（一覧）ページに渡す
		model.addAttribute("enquetes", enqueteService.findAllEnquete());
		
		// アンケート削除（一覧）ページに移動する
		return "management/delete_enquete";
	}
	
	/* アンケート削除（確認）ページを表示する */
	@PostMapping("/delete")
	public String deleteEnqueteSubmit(@RequestParam("enqueteId") Integer id, 
			Model model, RedirectAttributes attributes) {
		
		// 削除予定のアンケート情報をアンケート削除（確認）ページに渡す
		Enquete enquete = enqueteService.findByIdEnquete(id);
		model.addAttribute("enquete", enquete);
		
		// アンケート削除（確認）ページに移動する
		return "management/delete_enquete_confirm";
	}
	
	/* アンケートを削除する */
	@PostMapping("/delete/confirm")
	public String deleteEnqueteConfirmSubmit(@RequestParam("enqueteId") Integer id, 
			Model model, RedirectAttributes attributes) {
		
		// 削除対象アンケートの選択情報を削除する
		enqueteService.deleteByEnqueteIdResult(id);
		
		// 削除対象アンケートの情報を取得する
		Enquete enquete = enqueteService.findByIdEnquete(id);
		
		// アンケートタイトルを取得する
		String enqueteTitle = enquete.getEnqueteTitle();
		
		// 削除対象アンケートの質問の選択肢を削除する
		for(Question question : enquete.getQuestionList()){
			enqueteService.deleteByQuestionIdAnswer(question.getId());
			
		}
		
		// 削除対象アンケートの質問を削除する
		enqueteService.deleteByEnqueteIdQuestion(id);
		
		// 削除対象アンケートを削除する
		enqueteService.deleteByIdEnquete(id);
		
		// アンケート削除成功時のメッセージを渡す
		String message = "アンケート「" + enqueteTitle + "」が削除されました";
		attributes.addFlashAttribute("message", message);
		
		// アンケート削除（一覧）ページに移動する
		return "redirect:/management/delete";
	}
	
}
