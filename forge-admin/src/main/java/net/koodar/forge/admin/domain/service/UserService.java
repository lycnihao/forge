package net.koodar.forge.admin.domain.service;

import net.koodar.forge.admin.domain.entity.User;

import java.util.List;

/**
 * Domain用户业务接口
 *
 * @author liyc
 */
public interface UserService {

	List<User> listByIds(List<Long> userIds);

	User findById(Long userId);

	void save(User user);

	boolean checkExistsUsername(String username);

	void batchSave(List<User> users);

	void deleteUser(User user);

	void disabledUser(User user);

}
