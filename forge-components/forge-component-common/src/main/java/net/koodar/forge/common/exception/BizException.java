package net.koodar.forge.common.exception;

/**
 * BizException 预期异常
 *
 * @author liyc
 */
public class BizException extends AbstractException {

	private static final String DEFAULT_ERR_CODE = "BIZ_ERROR";

	public BizException(String errMessage) {
		super(DEFAULT_ERR_CODE, errMessage);
	}

	public BizException(String errCode, String errMessage) {
		super(errCode, errMessage);
	}

	public BizException(String errMessage, Throwable e) {
		super(DEFAULT_ERR_CODE, errMessage, e);
	}

	public BizException(String errorCode, String errMessage, Throwable e) {
		super(errorCode, errMessage, e);
	}

}
