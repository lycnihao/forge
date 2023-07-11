package net.koodar.forge.admin.handler;

import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.entity.OperateLog;
import net.koodar.forge.admin.repository.OperateLogRepository;
import net.koodar.forge.common.handler.Handler;
import net.koodar.forge.common.handler.IHandler;
import net.koodar.forge.common.module.operatelog.OperateLogEvent;

/**
 * @author liyc
 */
@Handler
@RequiredArgsConstructor
public class OperateLogEventHandler implements IHandler<OperateLogEvent> {

	private final OperateLogRepository operateLogRepository;

	@Override
	public void handler(OperateLogEvent event) {
		OperateLog operateLog =
				OperateLog.builder()
						.operateUserId(event.getOperateUserId())
						.operateUserName(event.getOperateUserName())
						.url(event.getUrl())
						.method(event.getMethod())
						.param(event.getParam())
						.ip(event.getIp())
						.userAgent(event.getUserAgent())
						.failReason(event.getFailReason())
						.successFlag(event.getSuccessFlag())
						.content(event.getContent())
						.module(event.getModule())
						.build();
		operateLogRepository.save(operateLog);
	}
}
