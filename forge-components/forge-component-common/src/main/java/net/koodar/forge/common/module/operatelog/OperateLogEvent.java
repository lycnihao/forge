package net.koodar.forge.common.module.operatelog;

import lombok.*;
import net.koodar.forge.common.event.IAsyncEvent;
import net.koodar.forge.common.event.IEvent;

/**
 * @author liyc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class OperateLogEvent implements IAsyncEvent {

	/**
	 * 操作人id
	 */
	private Long operateUserId;

	/**
	 * 操作人名称
	 */
	private String operateUserName;
	/**
	 * 操作模块
	 */
	private String module;

	/**
	 * 操作内容
	 */
	private String content;

	/**
	 * 请求路径
	 */
	private String url;

	/**
	 * 请求方法
	 */
	private String method;

	/**
	 * 请求参数
	 */
	private String param;

	/**
	 * 客户ip
	 */
	private String ip;

	/**
	 * user-agent
	 */
	private String userAgent;

	/**
	 * 请求结果 0失败 1成功
	 */
	private Boolean successFlag;

	/**
	 * 失败原因
	 */
	private String failReason;

}
