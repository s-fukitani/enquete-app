package katachi.spring.sample.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import katachi.spring.sample.entity.Answer;
import katachi.spring.sample.entity.Enquete;
import katachi.spring.sample.entity.Question;
import katachi.spring.sample.entity.Result;
import katachi.spring.sample.service.EnqueteService;
import lombok.RequiredArgsConstructor;

/* 
 * アンケートの集計を行い、結果を表示するコントローラ
 */
@Controller
@RequestMapping("/total")
@RequiredArgsConstructor
public class TotalListController {
	
	private final EnqueteService enqueteService;
	
	/* アンケート結果一覧ページを表示する */
	@GetMapping
	public String totalList(Model model) {
		
		// アンケート結果一覧ページにアンケート情報を渡す
		model.addAttribute("enquetes", enqueteService.findAllEnquete());
		
		// アンケート結果一覧ページに移動する
		return "total/total_list";
	}

	/* 選択したアンケート結果を表示する */
	@GetMapping("/{id}")
	public String total(@PathVariable Integer id, Model model, RedirectAttributes attributes) {
		
		// 選択したアンケート情報を取得する
		Enquete enquete = enqueteService.findByIdEnquete(id);
		
		// 選択したアンケートの全回答結果を集計する
		Map<Integer, Integer> countMap = new HashMap<>();
		List<Integer> sumList = new ArrayList<>();
		Integer answerSum = 0;
		for (Question question : enquete.getQuestionList()) {
			for (Answer answer : question.getAnswerList()) {
				Result result = new Result(enquete.getId(), question.getId(), answer.getId());
				countMap.put(result.getAnswerId(), 
						enqueteService.countEnqueteResult(enquete.getId(), question.getId(), answer.getId()));
				answerSum += enqueteService.countEnqueteResult(enquete.getId(), question.getId(), answer.getId());
			}
			// 1問毎の集計結果の合計を追加
			sumList.add(answerSum);
			answerSum = 0;
		}
		
		// 選択したアンケート情報と集計結果と1問毎の集計結果の合計を集計結果ページに渡す
		model.addAttribute("enquete", enquete);
		model.addAttribute("countMap", countMap);
		model.addAttribute("sumList", sumList);
		
		// 集計結果ページに移動する
		return "total/total";
	}
}
