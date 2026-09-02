package katachi.spring.sample.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/*
* アンケート作成（概略）フォーム
*/
@Data
@AllArgsConstructor
public class EnqueteOutlineForm {
	/* アンケートタイトル */
	@NotBlank(message = "アンケートタイトルが入力されていません")
	private String enqueteTitle;
	/* 質問数 */
	private Integer questionMax;
	/* 選択肢数 */
	private Integer answerMax;
	
	//質問数および選択肢数の初期化
	public EnqueteOutlineForm(){
		questionMax = 1;
		answerMax = 2;
	}
}
