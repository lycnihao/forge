package net.koodar.forge.admin.application.handler;

import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.entity.LoginLog;
import net.koodar.forge.admin.domain.repository.LoginLogRepository;
import net.koodar.forge.common.handler.Handler;
import net.koodar.forge.common.handler.IHandler;
import net.koodar.forge.common.module.loginlog.LoginLogEvent;

/**
 * 登录日志 listener
 *
 * @author liyc
 */
@Handler
@RequiredArgsConstructor
public class LoginLogEventListener implements IHandler<LoginLogEvent> {

	private final LoginLogRepository loginLogRepository;

	@Override
	public void handler(LoginLogEvent event) {
		LoginLog loginEntity = LoginLog.builder()
				.userId(event.getUserId())
				.userName(event.getUserName())
				.userAgent(event.getUserAgent())
				.loginIp(event.getLoginIp())
				.remark(event.getRemark())
				.loginResult(event.getLoginResult())
				.build();
		loginLogRepository.save(loginEntity);
	}
}
