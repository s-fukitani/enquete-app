package katachi.spring.sample.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import katachi.spring.sample.entity.Enquete;
import katachi.spring.sample.entity.EnqueteUser;
import katachi.spring.sample.entity.Result;
import katachi.spring.sample.entity.Role;

/**
 * リポジトリのインターフェース
 */
@Mapper
public interface EnqueteMapper {
	// 全アンケート情報の取得
	List<Enquete> selectEnqueteAll();
	
	// 対象IDのアンケート情報の取得
	Enquete selectEnqueteById(@Param("id") Integer id);
	
	// 1問毎の回答結果の登録
	void insertResult(Result result);
	
	// 指定された選択肢が選ばれた数の算出
	Integer countResultById(Integer enqueteId, Integer questionId, Integer answerId);
	
	// 指定されたユーザーが対象のアンケートを回答済みかどうかのチェックを行う（1以上が返ってきたときは回答済み）
	Integer countResultByEnqueteIdAndUserId(Integer enqueteId, Integer userId);
	
	// アンケートの登録
	void insertEnquete(String enqueteTitle);
	
	// 質問の登録
	void insertQuestion(Integer enqueteId, String questionText);
	
	// 選択肢の登録
	void insertAnswer(Integer questionId, String answerText);
	
	// 指定されたタイトルのアンケートのIDを取得
	Integer selectEnqueteIdByTitle(String enqueteTitle);
	
	// 既に同じタイトルのアンケートが登録されていないかのチェックを行う（1以上が返ってきたときは登録されている） 
	Integer countEnqueteByTitle(String enqueteTitle);
	
	// 指定されたアンケート内で指定されたテキストの質問のIDを取得
	Integer selectQuestionIdByText(String questionText, Integer enqueteId);
	
	// 指定された質問に属する全ての選択肢を削除
	void deleteAnswerByQuestionId(Integer questionId);
	
	// 指定されたアンケートの全ての質問の削除
	void deleteQuestionByEnqueteId(Integer enqueteId);
	
	// 指定されたアンケートの削除
	void deleteEnqueteById(Integer id);
	
	// 指定されたアンケートの回答結果の削除
	void deleteResultByEnqueteId(Integer enqueteId);
	
	// 削除対象のユーザーが回答したアンケートの回答結果の削除
	void deleteResultByUserId(Integer userId);
	
	// 指定されたユーザー名のユーザー情報を取得
	EnqueteUser selectByUserName(String userName);
	
	// 既に同じユーザー名のユーザーが登録されていないかのチェックを行う（1以上が返ってきたときは登録されている）
	Integer countUserByUserName(String userName);
	
	// 指定されたユーザーIDのユーザー情報を取得
	EnqueteUser selectByUserId(@Param("id") Integer id);
	
	// ユーザーの登録
	void insertUser(String userName, String password, Role authority);
	
	// ユーザーの更新
	void updateUser(@Param("id") Integer id, String userName, String password, Role authority);
	
	// ユーザーの削除
	void deleteUserById(@Param("id") Integer id);
	
	// 全ユーザー情報の取得
	List<EnqueteUser> selectUserAll();
	
}
