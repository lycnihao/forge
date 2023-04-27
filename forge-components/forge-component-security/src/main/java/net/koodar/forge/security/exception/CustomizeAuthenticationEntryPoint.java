package net.koodar.forge.security.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.koodar.forge.common.code.UserErrorCode;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.utils.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * 处理未授权异常
 *
 * @author liyc
 */
@Slf4j
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		Response errorDetail = handleBaseException(authException);
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(JsonUtils.objectToJson(errorDetail));
	}

	private Response handleBaseException(Throwable t) {
		Assert.notNull(t, "Throwable must not be null");

		Response errorDetail = new Response();
		errorDetail.setLevel(UserErrorCode.LEVEL_USER);

		if (t instanceof AccountExpiredException){
			errorDetail.setErrMessage("账户过期");
		} else if (t instanceof DisabledException){
			errorDetail.setErrMessage("账号被禁用");
		} else if (t instanceof LockedException){
			errorDetail.setErrMessage("账号认被锁定");
		} else if (t instanceof CredentialsExpiredException){
			errorDetail.setErrCode(UserErrorCode.LOGIN_STATE_INVALID.getCode());
			errorDetail.setErrMessage(UserErrorCode.LOGIN_STATE_INVALID.getMsg());
		} else if (t instanceof BadCredentialsException){
			errorDetail.setErrCode(UserErrorCode.LOGIN_BAD_CREDENTIALS.getCode());
			errorDetail.setErrMessage(UserErrorCode.LOGIN_BAD_CREDENTIALS.getMsg());
		} else {
			errorDetail.setErrCode(UserErrorCode.LOGIN_STATE_INVALID.getCode());
			errorDetail.setErrMessage(UserErrorCode.LOGIN_STATE_INVALID.getMsg());
		}

		return errorDetail;
	}
}
