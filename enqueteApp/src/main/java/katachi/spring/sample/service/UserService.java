package katachi.spring.sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import katachi.spring.sample.entity.EnqueteUser;
import katachi.spring.sample.entity.Role;

/**
 * ユーザー関連のサービスのインターフェース
 */
@Service
public interface UserService {
	// 指定されたユーザー名のユーザー情報を取得
	EnqueteUser findByUserNameEnqueteUser(String userName);
	// 全ユーザー情報の取得
	List<EnqueteUser> findAllUser();
	// 指定されたユーザーIDのユーザー情報を取得
	EnqueteUser findByIdEnqueteUser(Integer id);
	// 既に同じユーザー名のユーザーが登録されていないかのチェックを行う（trueが返ってきたときは登録済み）
	boolean checkSameUserName(String userName);
	// ユーザーの登録
	void insertUserRecord(String userName, String password, Role authority);
	// ユーザーの更新
	void updateUserRecord(Integer id, String userName, String password, Role authority);
	// ユーザーの削除
	void deleteByIdEnqueteUser(Integer id);
}
