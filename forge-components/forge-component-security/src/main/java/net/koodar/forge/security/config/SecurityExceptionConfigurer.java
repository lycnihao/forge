package net.koodar.forge.security.config;

import net.koodar.forge.security.exception.CustomizeAccessDeniedHandler;
import net.koodar.forge.security.exception.CustomizeAuthenticationEntryPoint;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * Security 异常配置
 *
 * @author liyc
 */
@Component
public class SecurityExceptionConfigurer implements SecurityConfigurer {
	@Override
	public void configure(HttpSecurity httpSecurity) {
		try {
			httpSecurity
					.exceptionHandling(exceptionHandling -> exceptionHandling
					// 认证失败处理类
					.authenticationEntryPoint(new CustomizeAuthenticationEntryPoint())
					// 授权失败处理类
					.accessDeniedHandler(new CustomizeAccessDeniedHandler()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
