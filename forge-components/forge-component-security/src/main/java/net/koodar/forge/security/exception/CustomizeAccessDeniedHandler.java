package net.koodar.forge.security.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.koodar.forge.common.code.UserErrorCode;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.utils.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 处理访问被拒绝异常
 *
 * @author liyc
 */
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		Response errorDetail = new Response();
		errorDetail.setLevel(UserErrorCode.LEVEL_USER);
		errorDetail.setErrCode(UserErrorCode.NO_PERMISSION.getCode());
		errorDetail.setErrMessage(UserErrorCode.NO_PERMISSION.getMsg());
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(JsonUtils.objectToJson(errorDetail));
	}

}
