package katachi.spring.sample.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import katachi.spring.sample.entity.EnqueteUser;
import katachi.spring.sample.entity.Role;
import katachi.spring.sample.repository.EnqueteMapper;
import katachi.spring.sample.service.UserService;
import lombok.RequiredArgsConstructor;

/**
 * ユーザー関連のサービスのインターフェースの実装クラス
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final EnqueteMapper enqueteMapper;
	
	// ユーザーの登録
	@Override
	public void insertUserRecord(String userName, String password, Role authority) {
		enqueteMapper.insertUser(userName, password, authority);
	}
	
	// ユーザーの更新
	@Override
	public void updateUserRecord(Integer id, String userName, String password, Role authority) {
		enqueteMapper.updateUser(id, userName, password, authority);
	}
	
	// 全ユーザー情報の取得
	@Override
	public List<EnqueteUser> findAllUser(){
		return enqueteMapper.selectUserAll();
	}
	
	// 指定されたユーザー名のユーザー情報を取得
	@Override
	public EnqueteUser findByUserNameEnqueteUser(String userName) {
		return enqueteMapper.selectByUserName(userName);
	}
	
	// 既に同じユーザー名のユーザーが登録されていないかのチェックを行う
	@Override
	public boolean checkSameUserName(String userName) {
		Integer userCount = enqueteMapper.countUserByUserName(userName);
		
		if(userCount > 0) {
			// 登録済み
			return true;
		}
		else {
			// 未登録
			return false;
		}
	}
	
	// 指定されたユーザーIDのユーザー情報を取得
	@Override
	public EnqueteUser findByIdEnqueteUser(Integer id) {
		return enqueteMapper.selectByUserId(id);
	}
	
	// ユーザーの削除
	@Override
	public void deleteByIdEnqueteUser(Integer id) {
		enqueteMapper.deleteUserById(id);
	}
}
