package katachi.spring.sample.form;

import java.util.Objects;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import katachi.spring.sample.entity.Role;
import lombok.Data;

/*
 * ユーザー作成フォーム
 */
@Data
public class MakeUserForm {
	/* ユーザー名 */
	@NotBlank(message = "ユーザー名が入力されていません")
	private String userName;
	/* 権限 */
	private Role authority;
	/* パスワード */
	@NotBlank(message = "パスワードが入力されていません")
	private String password;
	/* 確認用パスワード */
	@NotBlank(message = "確認用パスワードが入力されていません")
	private String confirmPassword;
	
	// 権限の初期化
	public MakeUserForm(){
		authority = Role.ADMIN;
	}
	
	// パスワードと確認用パスワードが一致するかのチェック
	@AssertTrue(message = "パスワードが一致しません")
	public boolean isSamePassword() {
		return Objects.equals(password, confirmPassword); 
	}
	
}
