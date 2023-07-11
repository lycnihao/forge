package net.koodar.forge.admin.manager.impl;


import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.entity.User;
import net.koodar.forge.admin.repository.UserRepository;
import net.koodar.forge.admin.manager.UserManager;
import net.koodar.forge.common.exception.BizException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Domain用户业务接口实现
 *
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class UserManagerImpl implements UserManager {

	private final UserRepository userRepository;

	@Override
	public List<User> listByIds(List<Long> userIds) {
		return userRepository.findByIdIn(userIds);
	}

	@Override
	public User findById(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new BizException(String.format("用户[%s]不存在", userId)));
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
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
	public void batchSave(List<User> users) {
		userRepository.saveAll(users);
	}

	@Override
	public void deleteUser(User user) {
		user.setDeletedFlag(true);
		userRepository.save(user);
	}

	@Override
	public void disabledUser(User user) {
		user.setStatus(0);
		userRepository.save(user);
	}
}
