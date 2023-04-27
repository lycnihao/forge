package net.koodar.forge.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.koodar.forge.common.code.ErrorCode;
import net.koodar.forge.common.code.SystemErrorCode;
import net.koodar.forge.common.code.UnexpectedErrorCode;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static net.koodar.forge.common.code.ErrorCodeRangeContainer.register;

/**
 * @author liyc
 */
public class ErrorCodeRangeContainerTest {

	@Test
	public void checkStartAndEnd() {
		// 结束值不能大于开始值
		Assertions.assertThrows(ExceptionInInitializerError.class, () ->
				register(SystemErrorCode.class, 10001, 10000));

		// 开始值不能小于 MIN_START_CODE
		Assertions.assertThrows(ExceptionInInitializerError.class, () ->
				register(SystemErrorCode.class, 999, 20000));

	}

	@Test
	public void checkDistinctError() {
		// 校验是否重复注册
		Assertions.assertThrows(ExceptionInInitializerError.class,() -> {
			register(SystemErrorCode.class, 10001, 20000);
			register(UnexpectedErrorCode.class, 20001, 20000);
		});
		// 校验 开始结束值 是否越界
		Assertions.assertThrows(ExceptionInInitializerError.class,() -> {
			register(SystemErrorCode.class, 10001, 20000);
			register(UnexpectedErrorCode.class, 20001, 30000);
		});
	}

	@Test
	public void success() {
		Assertions.assertDoesNotThrow(() -> {
			register(SystemErrorCode.class, 10001, 20000);
			register(UnexpectedErrorCode.class, 20001, 30000);
		});
	}

	@Test
	public void errorCode() {
		// 状态码不在开始值和结束值区间
		Assertions.assertThrows(IllegalArgumentException.class, () -> register(TestErrorCode1.class, 10001, 20000));
		// 状态码重复
		Assertions.assertThrows(IllegalArgumentException.class, () -> register(TestErrorCode2.class, 10001, 20000));

	}

	@Getter
	@AllArgsConstructor
	private enum TestErrorCode1 implements ErrorCode {

		TEST_ERROR_CODE(999, "异常"),
		;

		private final int code;

		private final String msg;

		private final String level;

		TestErrorCode1(int code, String msg) {
			this.code = code;
			this.msg = msg;
			this.level = "test";
		}
	}

	@Getter
	@AllArgsConstructor
	private enum TestErrorCode2 implements ErrorCode {

		TEST_ERROR_CODE1(10001, "10001"),
		TEST_ERROR_CODE2(10001, "10001"),
		;

		private final int code;

		private final String msg;

		private final String level;

		TestErrorCode2(int code, String msg) {
			this.code = code;
			this.msg = msg;
			this.level = "test";
		}
	}

}
