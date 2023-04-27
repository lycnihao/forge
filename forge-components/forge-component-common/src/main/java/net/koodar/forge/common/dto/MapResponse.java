package net.koodar.forge.common.dto;

import java.util.Collections;
import java.util.Map;

/**
 * Map类型的请求响应.
 *
 * @author liyc
 */
public class MapResponse extends Response {

	private Map<String, Object> data;

	public Map<String, Object> getData() {
		if (data == null) {
			return Collections.emptyMap();
		}
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public static MapResponse ok(Map<String, Object> data) {
		MapResponse response = new MapResponse();
		response.setSuccess(true);
		response.setData(data);
		return response;
	}

	public MapResponse add(String key, Object value) {
		this.getData().put(key, value);
		return this;
	}
}
