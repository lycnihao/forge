package net.koodar.forge.security.dynamicAuthorization;


import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

/**
 * 动态权限依赖倒置接口，通过实现该接口完成加载权限资源配置的操作
 *
 * @author liyc
 */
public interface DynamicSecurityService {

	/**
	 * 加载资源权限
	 * @return 资源权限 K.资源路径,V.key:权限标识 value:角色编码
	 */
	Map<String, Pair<String, String>> loadDataSource();

}
