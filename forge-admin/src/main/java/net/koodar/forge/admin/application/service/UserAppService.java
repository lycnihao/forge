package net.koodar.forge.admin.application.service;

import net.koodar.forge.common.dto.Response;

/**
 * @author liyc
 */
public interface UserAppService {

	Boolean isExistsByUsername(String username);

	void createAdministrator(String username, String password, String roleName);

	Response getUserInfo();

	Response updatePassword(String oldPassword, String newPassword);

}
