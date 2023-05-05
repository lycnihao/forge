package net.koodar.forge.admin.domain.service.impl;


import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.entity.User;
import net.koodar.forge.admin.domain.repository.UserRepository;
import net.koodar.forge.admin.domain.service.UserService;
import net.koodar.forge.common.exception.BizException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Domain用户业务接口实现
 *
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public User findById(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new BizException(String.format("用户[%s]不存在", userId)));
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public void updatePassword(Long userId, String oldPassword, String newPassword, PasswordEncoder passwordEncoder) throws BizException {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
				throw new BizException("原密码错误，请重新输入。");
			}
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
		}
	}

	@Override
	public boolean checkExistsUsername(String username) {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteUser(User user) {
		user.setDeletedFlag(true);
		userRepository.save(user);
	}
}
