package net.koodar.forge.admin.application.handler;

import net.koodar.forge.common.code.SystemErrorCode;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.dto.SingleResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 控制器返回格式统一处理
 *
 * @author liyc
 */
@ControllerAdvice("net.koodar.")
public class CommonResultControllerAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
	}

	@Override
	public Object beforeBodyWrite(Object body,
								  MethodParameter returnType,
								  MediaType selectedContentType,
								  Class<? extends HttpMessageConverter<?>> selectedConverterType,
								  ServerHttpRequest request, ServerHttpResponse response) {
		MappingJacksonValue container = getOrCreateContainer(body);
		// The contain body will never be null
		beforeBodyWriteInternal(container, selectedContentType, returnType, request, response);
		return container;
	}

	/**
	 * Wrap the body in a {@link MappingJacksonValue} value container (for providing
	 * additional serialization instructions) or simply cast it if already wrapped.
	 */
	private MappingJacksonValue getOrCreateContainer(Object body) {
		return body instanceof MappingJacksonValue ? (MappingJacksonValue) body :
				new MappingJacksonValue(body);
	}

	private void beforeBodyWriteInternal(MappingJacksonValue bodyContainer,
										 MediaType contentType,
										 MethodParameter returnType,
										 ServerHttpRequest request,
										 ServerHttpResponse response) {
		// Get return body
		Object returnBody = bodyContainer.getValue();

		if (returnBody instanceof Response responseDTO) {
			// If the return body is instance of Response DTO, then just do nothing
			if (SystemErrorCode.LEVEL_SYSTEM.equals(responseDTO.getLevel())) {
				response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				response.setStatusCode(HttpStatus.OK);
			}
			return;
		} else if (response instanceof ServletServerHttpResponse) {
			bodyContainer.setValue(SingleResponse.error(SystemErrorCode.SYSTEM_ERROR, null));
		} else {
			bodyContainer.setValue(SingleResponse.ok());
		}
	}
}
