package net.koodar.forge.security.config;

import net.koodar.forge.security.filter.AuthenticationTokenFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.stereotype.Component;

/**
 * JWT令牌校验过滤器配置
 *
 * @author liyc
 */
@Component
public class AuthenticationTokenConfig implements SecurityConfigurer {

	private final AuthenticationTokenFilter authenticationTokenFilter;

	public AuthenticationTokenConfig(AuthenticationTokenFilter authenticationTokenFilter) {
		this.authenticationTokenFilter = authenticationTokenFilter;
	}

	@Override
	public void configure(HttpSecurity httpSecurity) {
		httpSecurity
				.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(authenticationTokenFilter, LogoutFilter.class);
	}
}
