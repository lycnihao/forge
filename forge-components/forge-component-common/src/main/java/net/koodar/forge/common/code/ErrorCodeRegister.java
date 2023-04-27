package net.koodar.forge.common.code;


import static net.koodar.forge.common.code.ErrorCodeRangeContainer.register;

/**
 * 注册code状态码 <br>
 * ps：为什么要在此处不那么优雅的手动注册？
 * 主要是为了能统一、清晰、浏览当前定义的所有状态码
 * 方便后续维护
 *
 * @author liyc
 * @see <a href="https://github.com/1024-lab/smart-admin/blob/master/smart-admin-api/sa-common/src/main/java/net/lab1024/sa/common/common/code/ErrorCodeRegister.java">查看来源</a>
 */
public class ErrorCodeRegister {

	static {
		// 系统 错误码
		register(SystemErrorCode.class, 10001, 20000);

		// 意外 错误码
		register(UnexpectedErrorCode.class, 20001, 30000);

		// 用户 通用错误码
		register(UserErrorCode.class, 30001, 40000);
	}


	public static int initialize() {
		return ErrorCodeRangeContainer.initialize();
	}

	public static void main(String[] args) {
		ErrorCodeRegister.initialize();
	}


}
