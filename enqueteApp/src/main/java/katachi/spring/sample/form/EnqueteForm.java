package katachi.spring.sample.form;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * アンケートフォーム
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnqueteForm {
	/* ユーザーID */
	private Integer userId;
	/* アンケートID */
	private Integer enqueteId;
	/* 質問ID */
	private List<Integer> questionId;
	/* 選択肢 */
	private Map<Integer, Integer> answerMap;
}
