package katachi.spring.sample.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import katachi.spring.sample.entity.Enquete;
import katachi.spring.sample.entity.Result;
import katachi.spring.sample.repository.EnqueteMapper;
import katachi.spring.sample.service.EnqueteService;
import lombok.RequiredArgsConstructor;

/**
 * アンケート関連のサービスのインターフェースの実装クラス
 */
@Service
@Transactional
@RequiredArgsConstructor
public class EnqueteServiceImpl implements EnqueteService {
	
	private final EnqueteMapper enqueteMapper;
	
	// 全アンケート情報の取得
	@Override
	public List<Enquete> findAllEnquete() {
		return enqueteMapper.selectEnqueteAll();
	}
	
	// 対象IDのアンケート情報の取得 
	@Override
	public Enquete findByIdEnquete(Integer id) {
		return enqueteMapper.selectEnqueteById(id);
	}
	
	// 指定されたユーザーが対象のアンケートを回答済みかどうかのチェックを行う
	@Override
	public boolean checkAnsweredEnquete(Integer enqueteId, Integer userId) {
		Integer resultCount = enqueteMapper.countResultByEnqueteIdAndUserId(enqueteId, userId);
		
		if(resultCount > 0) {
			// 回答済み
			return true;
		}
		else {
			// 未回答
			return false;
		}
	}
	
	// 1問毎の回答結果の登録
	@Override
	public void insertEnqueteResult(Result result) {
		enqueteMapper.insertResult(result);
	}
	
	// アンケートの登録
	@Override
	public void insertEnqueteRecord(String enqueteTitle) {
		enqueteMapper.insertEnquete(enqueteTitle);
	}
	
	// 質問の登録
	@Override
	public void insertQuestionRecord(Integer enqueteId, String questionText) {
		enqueteMapper.insertQuestion(enqueteId, questionText);
	}
	
	// 選択肢の登録
	@Override
	public void insertAnswerRecord(Integer questioneId, String answerText) {
		enqueteMapper.insertAnswer(questioneId, answerText);
	}
	
	// 指定されたタイトルのアンケートのIDを取得 
	@Override
	public Integer findByTitleEnqueteId(String enqueteTitle) {
		return enqueteMapper.selectEnqueteIdByTitle(enqueteTitle);
	}
	
	// 既に同じタイトルのアンケートが登録されていないかのチェックを行う
	@Override
	public boolean checkEnqueteSameTitle(String enqueteTitle) {
		Integer enqueteCount = enqueteMapper.countEnqueteByTitle(enqueteTitle);
		
		if(enqueteCount > 0) {
			// 登録済み
			return true;
		}
		else {
			// 未登録
			return false;
		}
	}
	
	// 指定されたアンケート内で指定されたテキストの質問のIDを取得
	@Override
	public Integer findByTextQuestionId(String questionText, Integer enqueteId) {
		return enqueteMapper.selectQuestionIdByText(questionText, enqueteId);
	}
	
	// 指定された選択肢が選ばれた数の算出
	@Override
	public Integer countEnqueteResult(Integer enqueteId, Integer questionId, Integer answerId) { 
		return enqueteMapper.countResultById(enqueteId, questionId, answerId);		
	}
	
	// 指定された質問に属する全ての選択肢を削除
	@Override
	public void deleteByQuestionIdAnswer(Integer questioneId) {
		enqueteMapper.deleteAnswerByQuestionId(questioneId);
	}
	
	// 指定されたアンケートの全ての質問の削除
	@Override
	public void deleteByEnqueteIdQuestion(Integer enqueteId) {
		enqueteMapper.deleteQuestionByEnqueteId(enqueteId);
	}
	
	// 指定されたアンケートの削除
	@Override
	public void deleteByIdEnquete(Integer id) {
		enqueteMapper.deleteEnqueteById(id);
	}
	
	// 指定されたアンケートの回答結果の削除
	@Override
	public void deleteByEnqueteIdResult(Integer enqueteId) {
		enqueteMapper.deleteResultByEnqueteId(enqueteId);
	}
	
	// 削除対象のユーザーが回答したアンケートの回答結果の削除
	@Override
	public void deleteByUserIdResult(Integer userId) {
		enqueteMapper.deleteResultByUserId(userId);
	}
}
