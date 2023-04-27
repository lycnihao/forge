package net.koodar.forge.security.config;

import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

/**
 * 动态权限配置
 *
 * @author liyc
 */
@Component
public class DynamicSecurityConfig implements SecurityConfigurer {

	private final AuthorizationManager<RequestAuthorizationContext> dynamicAuthorizationManager;

	public DynamicSecurityConfig(AuthorizationManager<RequestAuthorizationContext> dynamicAuthorizationManager) {
		this.dynamicAuthorizationManager = dynamicAuthorizationManager;
	}

	@Override
	public void configure(HttpSecurity httpSecurity) {
		try {
			httpSecurity
					.authorizeHttpRequests()
					// 动态权限认证（默认都需要登录）
					.anyRequest().access(dynamicAuthorizationManager);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
