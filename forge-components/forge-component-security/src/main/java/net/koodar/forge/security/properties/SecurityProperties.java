package net.koodar.forge.security.properties;

import lombok.Data;

/**
 * Security 属性
 *
 * @author liyc
 */
public class SecurityProperties {

	private final Initializer initializer = new Initializer();

	@Data
	public static class Initializer {

		/**
		 * 超级管理员账号
		 */
		private String superAdminUsername = "admin";

		/**
		 * 超级管理员密码
		 */
		private String superAdminPassword;

		/**
		 * 登录接口地址
		 */
		private String loginProcessingUrl = "/user/login";

		/**
		 * 退出登录接口地址
		 */
		private String logoutUrl = "/user/logout";

		/**
		 * 启用禁用CSRF
		 */
		private boolean disableCsrf = true;

		/**
		 * 启用禁用CORS
		 */
		private boolean disableCors = true;

		/**
		 * JWT 有效时间(毫秒)
		 * 15分钟有效期 1000*60*15=900000
		 */
		private int jwtTime = 900000;

	}

}
