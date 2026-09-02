package katachi.spring.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import katachi.spring.sample.entity.EnqueteUser;
import katachi.spring.sample.entity.LoginUser;
import katachi.spring.sample.entity.Role;
import katachi.spring.sample.repository.EnqueteMapper;
import lombok.RequiredArgsConstructor;

/**
* カスタム認証サービス
*/
@Service
@RequiredArgsConstructor
public class LoginUserDatailsServiceImpl implements UserDetailsService {
	
	private final EnqueteMapper enqueteMapper;
	
	// 指定されたユーザー名のユーザー情報に基づいたUserDetailsの実装クラスを取得する
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		 	// ユーザーテーブルからデータを取得
		 	EnqueteUser enqueteUser = enqueteMapper.selectByUserName(username);
	
		 	// 対象データがあれば、UserDetailsの実装クラスを返す
		 	if (enqueteUser != null) {
		 		// 対象データが存在する
		 		// UserDetailsの実装クラスを返す
		 		return new LoginUser(enqueteUser.getUserName(), 
		 				enqueteUser.getPassword(), 
					 	getAuthorityList(enqueteUser.getAuthority())
					 	);
		 	} 
		 	else {
		 		// 対象データが存在しない
		 		throw new UsernameNotFoundException(
		 				username + " => 指定しているユーザー名は存在しません");
		 	}
	 }
	
	 // 権限情報をリストで取得する
	 private List<GrantedAuthority> getAuthorityList(Role role) {
		 // 権限リスト
		 List<GrantedAuthority> authorities = new ArrayList<>();
		 // 列挙型からロールを取得
		 authorities.add(new SimpleGrantedAuthority(role.name()));
		 // ADMIN ロールの場合、USERの権限も付与
		 if (role == Role.ADMIN) {
			 authorities.add(
					 new SimpleGrantedAuthority(Role.USER.toString()));
		 }
		 return authorities;
	 }
}
