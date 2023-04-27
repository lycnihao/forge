package net.koodar.forge.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Security 自定义配置
 *
 * @author liyc
 */
public interface SecurityConfigurer {

	/**
	 * configure 配置
	 * @param httpSecurity httpSecurity
	 */
	void configure(HttpSecurity httpSecurity);

}
