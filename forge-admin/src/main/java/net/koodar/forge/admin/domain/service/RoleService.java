package net.koodar.forge.admin.domain.service;

import net.koodar.forge.admin.domain.entity.Role;

/**
 * @author liyc
 */
public interface RoleService {

	void deleteRole(Long id);

	Role findById(Long id);

}
