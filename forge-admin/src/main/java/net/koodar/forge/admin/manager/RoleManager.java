package net.koodar.forge.admin.manager;

import net.koodar.forge.admin.domain.entity.Role;

/**
 * @author liyc
 */
public interface RoleManager {

	void deleteRole(Long id);

	Role findById(Long id);

}
