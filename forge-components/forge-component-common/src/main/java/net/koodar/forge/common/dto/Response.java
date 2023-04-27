package net.koodar.forge.common.dto;

import net.koodar.forge.common.code.ErrorCode;

/**
 * 请求响应.
 *
 * @author liyc
 */
public class Response extends DTO {

	private static final long serialVersionUID = 1L;

	private boolean success;

	private Integer errCode;

	private String level;

	private String errMessage;

	private String devMessage;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDevMessage() {
		return devMessage;
	}

	public void setDevMessage(String devMessage) {
		this.devMessage = devMessage;
	}

	@Override
	public String toString() {
		return "Response [success=" + success + ", errCode=" + errCode + ", errMessage=" + errMessage + "]";
	}


	public static Response ok() {
		Response response = new Response();
		response.setSuccess(true);
		return response;
	}

	public static Response error(Integer errCode, String level, String errMessage) {
		Response response = new Response();
		response.setSuccess(false);
		response.setErrCode(errCode);
		response.setLevel(level);
		response.setErrMessage(errMessage);
		return response;
	}

	public static Response error(ErrorCode errorCode, String errMessage) {
		Response response = new Response();
		response.setSuccess(false);
		response.setErrCode(errorCode.getCode());
		response.setLevel(errorCode.getLevel());
		response.setErrMessage(errMessage);
		return response;
	}

}
