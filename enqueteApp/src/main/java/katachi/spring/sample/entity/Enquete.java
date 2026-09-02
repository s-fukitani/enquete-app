package katachi.spring.sample.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * アンケートクラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enquete {
	/* アンケートID */
	private Integer id;
	/* アンケートタイトル */
	private String enqueteTitle;
	/* 質問 */
	private List<Question> questionList;
}
