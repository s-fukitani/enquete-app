package katachi.spring.sample.form;

import lombok.Data;

/*
 * ログインフォーム
 */
@Data
public class LoginForm {
	 /* ユーザー名 */
	 private String userNameInput;
	 /* パスワード */
	 private String passwordInput;
}
