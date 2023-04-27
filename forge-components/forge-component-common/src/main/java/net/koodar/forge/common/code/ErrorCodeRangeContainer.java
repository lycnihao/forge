package net.koodar.forge.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 错误码 注册容器
 *
 * @author liyc
 * @see <a href="https://github.com/1024-lab/smart-admin/blob/master/smart-admin-api/sa-common/src/main/java/net/lab1024/sa/common/common/code/ErrorCodeRangeContainer.java">查看来源</a>
 */
public class ErrorCodeRangeContainer {

	/**
	 * 所有的错误码均大于10000
	 */
	static final int MIN_START_CODE = 10000;

	static final Map<Class<? extends ErrorCode>, CodeRange> CODE_RANGE_MAP = new ConcurrentHashMap<>();

	/**
	 * 用于统计数量
	 */
	static int errorCounter = 0;

	/**
	 * 注册状态码
	 * 校验是否重复 是否越界
	 *
	 * @param clazz
	 * @param start
	 * @param end
	 */
	public static void register(Class<? extends ErrorCode> clazz, int start, int end) {
		String simpleName = clazz.getSimpleName();
		if (!clazz.isEnum()) {
			throw new ExceptionInInitializerError(String.format("<<ErrorCodeRangeValidator>> error: %s not Enum class !", simpleName));
		}
		if (start > end) {
			throw new ExceptionInInitializerError(String.format("<<ErrorCodeRangeValidator>> error: %s start must be less than the end !", simpleName));
		}

		if (start <= MIN_START_CODE) {
			throw new ExceptionInInitializerError(String.format("<<ErrorCodeRangeValidator>> error: %s start must be more than %s !", simpleName, MIN_START_CODE));
		}

		// 校验是否重复注册
		boolean containsKey = CODE_RANGE_MAP.containsKey(clazz);
		if (containsKey) {
			throw new ExceptionInInitializerError(String.format("<<ErrorCodeRangeValidator>> error: Enum %s already exist !", simpleName));
		}

		// 校验 开始结束值 是否越界
		CODE_RANGE_MAP.forEach((k, v) -> {
			if (isExistOtherRange(start, end, v)) {
				throw new IllegalArgumentException(String.format("<<ErrorCodeRangeValidator>> error: %s[%d,%d] has intersection with class:%s[%d,%d]", simpleName, start, end,
						k.getSimpleName(), v.getStart(), v.getEnd()));
			}
		});

		// 循环校验code并存储
		List<Integer> codeList = Stream.of(clazz.getEnumConstants()).map(codeEnum -> {
			int code = codeEnum.getCode();
			if (code < start || code > end) {
				throw new IllegalArgumentException(String.format("<<ErrorCodeRangeValidator>> error: %s[%d,%d] code %d out of range", simpleName, start, end, code));
			}
			return code;
		}).collect(Collectors.toList());

		// 校验code是否重复
		List<Integer> distinctCodeList = codeList.stream().distinct().collect(Collectors.toList());

		Collection<Integer> subtract = subtract(codeList, distinctCodeList);
		if (!subtract.isEmpty()) {
			throw new IllegalArgumentException(String.format("<<ErrorCodeRangeValidator>> error: %s code %s is repeat!", simpleName, subtract));
		}

		CODE_RANGE_MAP.put(clazz, new CodeRange(start, end));
		// 统计
		errorCounter = errorCounter + distinctCodeList.size();
	}

	/**
	 * 是否存在于其他范围
	 *
	 * @param start
	 * @param end
	 * @param range
	 * @return
	 */
	private static boolean isExistOtherRange(int start, int end, CodeRange range) {
		if (start >= range.getStart() && start <= range.getEnd()) {
			return true;
		}

		if (end >= range.getStart() && end <= range.getEnd()) {
			return true;
		}

		return false;
	}

	private static List<Integer> subtract(final Collection<Integer> a,
										 final Collection<Integer> b) {
		Map<Integer, Integer> aMap = new HashMap<>(a.size());
		for (Integer key : a) {
			if (aMap.containsKey(key)) {
				aMap.put(key, aMap.get(key) +1);
			} else {
				aMap.put(key, 1);
			}
		}

		List<Integer> distinct = new ArrayList<>();
		for (Integer key : b) {
			if (aMap.get(key) > 1) {
				distinct.add(key);
			}
		}

		return distinct;
	}

	/**
	 * 进行初始化
	 */
	static int initialize() {
		return errorCounter;
	}

	@Getter
	@AllArgsConstructor
	public static class CodeRange {
		private Integer start;
		private Integer end;
	}
}
