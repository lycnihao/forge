package net.koodar.forge.common.exception;

/**
 * AbstractException是所有异常的父类.
 *
 * @author liyc
 */
public abstract class AbstractException extends RuntimeException {

	private String errCode;

	public AbstractException(String errMessage) {
		super(errMessage);
	}

	public AbstractException(String errCode, String errMessage) {
		super(errMessage);
		this.errCode = errCode;
	}

	public AbstractException(String errMessage, Throwable e) {
		super(errMessage, e);
	}

	public AbstractException(String errCode, String errMessage, Throwable e) {
		super(errMessage, e);
		this.errCode = errCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

}
