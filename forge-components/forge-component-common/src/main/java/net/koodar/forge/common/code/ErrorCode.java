package net.koodar.forge.common.code;

/**
 * 错误码<br>
 * 一共分为三种： 1）系统错误、2）用户级别错误、3）未预期到的错误
 *
 * @author liyc
 * @see <a href="https://github.com/1024-lab/smart-admin/blob/master/smart-admin-api/sa-common/src/main/java/net/lab1024/sa/common/common/code/ErrorCode.java">查看来源</a>
 */
public interface ErrorCode {

	/**
	 * 系统等级
	 */
	String LEVEL_SYSTEM = "system";

	/**
	 * 用户等级
	 */
	String LEVEL_USER = "user";

	/**
	 * 未预期到的等级
	 */
	String LEVEL_UNEXPECTED = "unexpected";

	/**
	 * 错误码
	 *
	 * @return
	 */
	int getCode();

	/**
	 * 错误消息
	 *
	 * @return
	 */
	String getMsg();

	/**
	 * 错误等级
	 *
	 * @return
	 */
	String getLevel();

}
