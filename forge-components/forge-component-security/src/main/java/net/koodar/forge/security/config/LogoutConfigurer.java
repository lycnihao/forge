package net.koodar.forge.security.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.utils.JsonUtils;
import net.koodar.forge.security.properties.SecurityProperties;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * 登出配置
 *
 * @author liyc
 */
@Component
public class LogoutConfigurer implements SecurityConfigurer {

	private final SecurityProperties.Initializer initializer;

	public LogoutConfigurer() {
		this.initializer = new SecurityProperties.Initializer();
	}

	@Override
	public void configure(HttpSecurity httpSecurity) {
		try {
			httpSecurity
					.logout((authorize) -> authorize
						.logoutUrl(initializer.getLogoutUrl())
						.logoutSuccessHandler(new CustomizeLogoutSuccessHandler()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Slf4j
	public static class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {

		@Override
		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

			if (Objects.isNull(authentication)) {
				return;
			}

			response.setCharacterEncoding("utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write(JsonUtils.objectToJson(Response.ok()));

			log.info("You have been logged out, looking forward to your next visit!");
		}
	}
}
