package net.koodar.forge.security.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.koodar.forge.common.dto.SingleResponse;
import net.koodar.forge.security.exception.CustomizeAuthenticationEntryPoint;
import net.koodar.forge.security.jwt.JwtService;
import net.koodar.forge.security.properties.SecurityProperties;
import net.koodar.forge.common.utils.JsonUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * 表单登录配置
 *
 * @author liyc
 */
@Slf4j
@Configuration
public class FormLoginConfigurer implements SecurityConfigurer {

	private final JwtService jwtService;
	private final SecurityProperties.Initializer initializer;

	public FormLoginConfigurer() {
		this.initializer = new SecurityProperties.Initializer();
		this.jwtService = new JwtService();
	}

	@Override
	public void configure(HttpSecurity httpSecurity) {
		try {
			httpSecurity
					.formLogin((authorize) -> authorize.permitAll()
							.loginProcessingUrl(initializer.getLoginProcessingUrl())
							.successHandler(new CustomizeAuthenticationSuccessHandler())
							.failureHandler(new AuthenticationEntryPointFailureHandler(new CustomizeAuthenticationEntryPoint())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

			User userDetails = (User) authentication.getPrincipal();

			// 生成Token
			String jwtToken = jwtService.generateToken(userDetails);

			AuthToken token = new AuthToken();
			token.setAccessToken(jwtToken);
			token.setExpiredIn(initializer.getJwtTime());

			response.setCharacterEncoding("utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write(JsonUtils.objectToJson(SingleResponse.ok(token)));

		}
	}

	@Data
	public static class AuthToken {

		/**
		 * Access token.
		 */
		@JsonProperty("access_token")
		private String accessToken;

		/**
		 * Expired in. (seconds)
		 */
		@JsonProperty("expired_in")
		private int expiredIn;

		/**
		 * Refresh token.
		 */
		@Deprecated
		@JsonProperty("refresh_token")
		private String refreshToken;

	}
}
