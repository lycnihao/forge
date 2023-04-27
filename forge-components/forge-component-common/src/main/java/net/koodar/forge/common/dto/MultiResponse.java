package net.koodar.forge.common.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 集合类型的请求响应.
 *
 * @author liyc
 */
public class MultiResponse<T> extends Response {

	private static final long serialVersionUID = 1L;

	private Collection<T> data;

	public List<T> getData() {
		if (null == data) {
			return Collections.emptyList();
		}
		if (data instanceof List) {
			return (List<T>) data;
		}
		return new ArrayList<>(data);
	}

	public void setData(Collection<T> data) {
		this.data = data;
	}

	public boolean isEmpty() {
		return data == null || data.isEmpty();
	}

	public boolean isNotEmpty() {
		return !isEmpty();
	}

	public static <T> MultiResponse<T> ok(Collection<T> data) {
		MultiResponse response = new MultiResponse();
		response.setSuccess(true);
		response.setData(data);
		return response;
	}


}
