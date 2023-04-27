package net.koodar.forge.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统错误状态码（此类返回码应该高度重视）
 *
 * @author liyc
 * @see <a href="https://github.com/1024-lab/smart-admin/blob/master/smart-admin-api/sa-common/src/main/java/net/lab1024/sa/common/common/code/SystemErrorCode.java">查看来源</a>
 */
@Getter
@AllArgsConstructor
public enum SystemErrorCode implements ErrorCode {

	SYSTEM_ERROR(10001, "系统似乎出现了点小问题"),

	;

	private final int code;

	private final String msg;

	private final String level;

	SystemErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
		this.level = LEVEL_SYSTEM;
	}
}
