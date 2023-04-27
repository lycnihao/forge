package net.koodar.forge.admin.domain.service;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Domain用户业务接口
 *
 * @author liyc
 */
public interface UserService {

	void updatePassword(Long userId, String oldPassword, String newPassword, PasswordEncoder passwordEncoder);

}
