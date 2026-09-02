package katachi.spring.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 回答結果クラス
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
	/* 回答結果ID */
	private Integer id;
	/* アンケートID */
	private Integer enqueteId;
	/* 質問ID */
	private Integer questionId;
	/* 選択肢ID */
	private Integer answerId;
	/* ユーザーID */
	private Integer userId;
	
	// 回答結果の設定（アンケートID、質問ID、選択肢ID）
	public Result(Integer enqueteId, Integer questionId, Integer answerId) {
		this.enqueteId = enqueteId;
		this.questionId = questionId;
		this.answerId = answerId;
	}
	
	// 集計のために同じ回答結果が存在するかをチェックする（アンケートID、質問ID、選択肢IDを比較）
	@Override
	public boolean equals(Object other) {
		if (other instanceof Result) {
			Result result = (Result) other;
			return this.enqueteId == result.getEnqueteId() &&
					this.questionId == result.getQuestionId() &&
					this.answerId == result.getAnswerId();
		}
		return super.equals(other);
	}
	
	// 上のメソッドにおいてオブジェクトを比較するために必要
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
