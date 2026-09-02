package katachi.spring.sample.service;

import java.util.List;

import katachi.spring.sample.entity.Enquete;
import katachi.spring.sample.entity.Result;

/**
 * アンケート関連のサービスのインターフェース
 */
public interface EnqueteService {
	// 全アンケート情報の取得
	List<Enquete> findAllEnquete();
	
	// 対象IDのアンケート情報の取得
	Enquete findByIdEnquete(Integer id);
	
	// 1問毎の回答結果の登録
	void insertEnqueteResult(Result result);
	
	// 指定された選択肢が選ばれた数の算出
	Integer countEnqueteResult(Integer enqueteId, Integer questionId, Integer answerId);
	
	// 指定されたユーザーが対象のアンケートを回答済みかどうかのチェックを行う（回答済みの時はtrue）
	boolean checkAnsweredEnquete(Integer enqueteId, Integer userId);
	
	// アンケートの登録
	void insertEnqueteRecord(String enqueteTitle);
	
	// 質問の登録
	void insertQuestionRecord(Integer enqueteId, String questionText);
	
	// 選択肢の登録
	void insertAnswerRecord(Integer questioneId, String answerText);
	
	// 指定されたタイトルのアンケートのIDを取得 
	Integer findByTitleEnqueteId(String enqueteTitle);
	
	// 既に同じタイトルのアンケートが登録されていないかのチェックを行う（登録済みの時はtrue）
	boolean checkEnqueteSameTitle(String enqueteTitle);
	
	// 指定されたアンケート内で指定されたテキストの質問のIDを取得
	Integer findByTextQuestionId(String questionText, Integer enqueteId);
	
	// 指定された質問に属する全ての選択肢を削除
	void deleteByQuestionIdAnswer(Integer questioneId);

	// 指定されたアンケートの全ての質問の削除
	void deleteByEnqueteIdQuestion(Integer enqueteId);
	
	// 指定されたアンケートの削除
	void deleteByIdEnquete(Integer id);
	
	// 指定されたアンケートの回答結果の削除
	void deleteByEnqueteIdResult(Integer enqueteId);
	
	// 削除対象のユーザーが回答したアンケートの回答結果の削除
	void deleteByUserIdResult(Integer userId);
}
