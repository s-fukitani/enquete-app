package katachi.spring.sample.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 質問クラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
	/* 質問ID */
	private Integer id;
	/* 質問テキスト */
	private String questionText;
	/* 選択肢 */
	private List<Answer> answerList;
}
