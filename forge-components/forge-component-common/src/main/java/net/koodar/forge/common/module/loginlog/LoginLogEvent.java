package net.koodar.forge.common.module.loginlog;

import lombok.*;
import net.koodar.forge.common.event.IAsyncEvent;
import net.koodar.forge.common.event.IEvent;

/**
 * 登录日志
 *
 * @author liyc
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class LoginLogEvent implements IAsyncEvent {

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 登录ip
	 */
	private String loginIp;

	/**
	 * user-agent
	 */
	private String userAgent;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 登录类型
	 */
	private Integer loginResult;

}
