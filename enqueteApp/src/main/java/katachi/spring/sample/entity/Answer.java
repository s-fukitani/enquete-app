package katachi.spring.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 選択肢クラス
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
	/* 選択肢ID */
	private Integer id;
	/* 選択肢テキスト */
	private String answerText;
}
