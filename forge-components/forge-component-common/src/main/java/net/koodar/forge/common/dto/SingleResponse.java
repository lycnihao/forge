package net.koodar.forge.common.dto;

import net.koodar.forge.common.code.ErrorCode;

/**
 * 请求响应.
 *
 * @author liyc
 */
public class SingleResponse<T> extends Response {

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static <T> SingleResponse<T> ok(T data) {
		SingleResponse<T> response = new SingleResponse<>();
		response.setSuccess(true);
		response.setData(data);
		return response;
	}

}
