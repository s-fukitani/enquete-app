package katachi.spring.sample.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/*
 * ログインユーザークラス
 */
public class LoginUser extends User {
	 // 最低限の情報を保持したUserDetails実装クラスUserを作成する
	 public LoginUser(String username,
			 String password,
			 Collection<? extends GrantedAuthority> authorities) {
		 
			 super(username, password, authorities);
			 
	}
}
