package katachi.spring.sample.entity;

/*
 * 権限の列挙型
 */
public enum Role {
	 ADMIN(0),	// 管理者
	 USER(1);	// 一般ユーザー

	/* 値を格納する変数 */
	private final int value;
	
	// 値を設定する
	Role(int value) {
		this.value = value;
	}
	
	// 値を取得する
	public int getValue() {
        return this.value;
    }
	
	// 値に対応した文字列を取得する
	public String getRoleString() {
		if(this.value == 0) {
			return "管理者";
		}
		else if(this.value == 1){
			return "一般ユーザー";
		}
		else {
			return "該当なし";
		}
	}
}