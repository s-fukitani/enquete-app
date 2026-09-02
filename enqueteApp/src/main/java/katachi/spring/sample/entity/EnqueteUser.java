package katachi.spring.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * ユーザークラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnqueteUser {
	 /* ID */
	 private Integer id;
	 /* ユーザー名 */
	 private String userName;
	 /* パスワード */
	 private String password;
	 /* 権限 */
	 private Role authority;
}
