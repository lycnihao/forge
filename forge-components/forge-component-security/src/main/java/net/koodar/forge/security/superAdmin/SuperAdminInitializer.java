package net.koodar.forge.security.superAdmin;

import lombok.extern.slf4j.Slf4j;
import net.koodar.forge.security.properties.SecurityProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;

/**
 * 超级管理员初始化处理，
 *
 * @author liyc
 */
@Slf4j
public abstract class SuperAdminInitializer {

	public static final String SUPER_ROLE_NAME = "super-role";

	private final SecurityProperties.Initializer initializer;

	public SuperAdminInitializer(SecurityProperties.Initializer initializer) {
		this.initializer = initializer;
	}

	@EventListener
	public void initialize(ApplicationReadyEvent readyEvent) {
		if (!isExistsSuperAdministratorUser()) {
			generateAdministratorUser();
		}
	}

	/**
	 * 是否存在超级管理员
	 * @return true: 存在, false: 不存在
	 */
	protected abstract boolean isExistsSuperAdministratorUser();

	/**
	 * 创建超级管理员
	 */
	protected abstract void generateAdministratorUser();

	/**
	 * 获取用户名
	 * @return 用户名
	 */
	protected final String getSuperAdminUsername() {
		return this.initializer.getSuperAdminUsername();
	}

	/**
	 * 获取密码，如果没有配置密码则会自动生成密码
	 * @return 密码
	 */
	protected final String getPassword() {
		String password = this.initializer.getSuperAdminPassword();
		if (!StringUtils.hasText(password)) {
			// generate password
			password = randomAlphanumeric();
			log.info("=== Generated random password: {} for super administrator: {} ===",
					password, this.initializer.getSuperAdminUsername());
		}
		return password;
	}

	private String randomAlphanumeric() {
		SecureRandom secureRandom = new SecureRandom();
		String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(16);
		for (int i = 0; i < 16; i++) {
			int randomIndex = secureRandom.nextInt(alphanumeric.length());
			char randomChar = alphanumeric.charAt(randomIndex);
			sb.append(randomChar);
		}
		return sb.toString();
	}
}
