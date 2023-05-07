package net.koodar.forge.admin.domain.service;

import net.koodar.forge.admin.domain.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Domain用户业务接口
 *
 * @author liyc
 */
public interface UserService {

	User findById(Long userId);

	void save(User user);

	boolean checkExistsUsername(String username);

	void deleteUser(User user);

}
