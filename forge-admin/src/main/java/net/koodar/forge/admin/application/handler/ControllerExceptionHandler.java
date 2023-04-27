package net.koodar.forge.admin.application.handler;

import lombok.extern.slf4j.Slf4j;
import net.koodar.forge.common.code.SystemErrorCode;
import net.koodar.forge.common.code.UserErrorCode;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.dto.SingleResponse;
import net.koodar.forge.common.exception.BizException;
import net.koodar.forge.common.exception.SysException;
import net.koodar.forge.common.utils.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 控制器异常统一处理
 *
 * @author liyc
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

	/**
	 * json 格式错误 缺少请求体
	 */
	@ResponseBody
	@ExceptionHandler(BindException.class)
	public ResponseEntity<?> paramExceptionHandler(BindException e) {
		log.error("全局参数异常,URL:{}", getCurrentRequestUrl(), e);
		List<FieldError> fieldErrors = e.getFieldErrors();
		List<String> error = fieldErrors.stream().map(field -> field.getField() + ":" + field.getDefaultMessage()).collect(Collectors.toList());
		String errorMsg = String.join(",", error);
		Response jsonResult = SingleResponse.error(UserErrorCode.PARAM_ERROR, errorMsg);
		return new ResponseEntity<>(jsonResult, HttpStatus.OK);
	}

	/**
	 * 业务异常
	 */
	@ExceptionHandler({SysException.class, BizException.class})
	public ResponseEntity<Response> handleServiceException(SysException e) {
		log.error("捕获业务异常,URL:{}", getCurrentRequestUrl(), e);
		Response jsonResult = handleBaseException(e);
		jsonResult.setLevel(SystemErrorCode.LEVEL_SYSTEM);
		jsonResult.setErrCode(SystemErrorCode.SYSTEM_ERROR.getCode());
		jsonResult.setErrMessage(e.getMessage());
		return new ResponseEntity<>(jsonResult, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 其他全部异常
	 */
	@ResponseBody
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<Response> errorHandler(Throwable e) {
		log.error("捕获全局异常,URL:{}", getCurrentRequestUrl(), e);
		Response jsonResult = Response.error(SystemErrorCode.SYSTEM_ERROR, e.toString());
		return new ResponseEntity<>(jsonResult, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private Response handleBaseException(Throwable t) {
		Assert.notNull(t, "Throwable must not be null");

		log.error("Captured an exception", t);

		Response jsonResult = new Response();
		jsonResult.setErrMessage(t.getMessage());
		if (log.isDebugEnabled()) {
			jsonResult.setDevMessage(ExceptionUtils.getStackTrace(t));
		}

		return jsonResult;
	}

	/**
	 * 获取当前请求url
	 */
	private String getCurrentRequestUrl() {
		RequestAttributes request = RequestContextHolder.getRequestAttributes();
		if (null == request) {
			return null;
		}
		ServletRequestAttributes servletRequest = (ServletRequestAttributes) request;
		return servletRequest.getRequest().getRequestURI();
	}

}
