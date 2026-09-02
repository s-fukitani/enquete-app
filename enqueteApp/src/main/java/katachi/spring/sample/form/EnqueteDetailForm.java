package katachi.spring.sample.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * アンケート作成（詳細）フォーム
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnqueteDetailForm {
	/* アンケートタイトル */
	private String enqueteTitle;
	/* 質問数 */
	private Integer questionMax;
	/* 選択肢数 */
	private Integer answerMax;
	/* 質問 */
	private String[] questions;
	/* 選択肢 */
	private String[][] answers;
}
