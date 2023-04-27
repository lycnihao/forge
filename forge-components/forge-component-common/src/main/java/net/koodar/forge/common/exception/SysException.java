package net.koodar.forge.common.exception;

/**
 * System Exception 未预期异常
 *
 * @author liyc
 */
public class SysException extends AbstractException {

	private static final String DEFAULT_ERR_CODE = "SYS_ERROR";

	public SysException(String errMessage) {
		super(DEFAULT_ERR_CODE, errMessage);
	}

	public SysException(String errCode, String errMessage) {
		super(errCode, errMessage);
	}

	public SysException(String errMessage, Throwable e) {
		super(DEFAULT_ERR_CODE, errMessage, e);
	}

	public SysException(String errorCode, String errMessage, Throwable e) {
		super(errorCode, errMessage, e);
	}

}
