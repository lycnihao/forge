package net.koodar.forge.common.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 与Client通信的对象.
 *
 * @author liyc
 */
public class ClientObject {

	private static final long serialVersionUID = 1L;

	/**
	 * 扩展值
	 */
	protected Map<String, Object> extValues = new HashMap<String, Object>();

	public Object getExtField(String key){
		if(extValues != null){
			return extValues.get(key);
		}
		return null;
	}

	public void putExtField(String fieldName, Object value){
		this.extValues.put(fieldName, value);
	}

	public Map<String, Object> getExtValues() {
		return extValues;
	}

	public void setExtValues(Map<String, Object> extValues) {
		this.extValues = extValues;
	}

}
