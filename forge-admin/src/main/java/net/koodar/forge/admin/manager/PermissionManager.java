package net.koodar.forge.admin.manager;

import net.koodar.forge.admin.domain.entity.Permission;

import java.util.Collection;
import java.util.List;

/**
 * @author liyc
 */
public interface PermissionManager {

	List<Permission> allPermission();

	List<Permission> listFunctionPermission();

	List<Permission> listMenuPermission();

	List<Permission> listPermissionByRoleIds(Collection<Long> roleIds);

	List<Permission> listMenuPermissionByRoleIds(Collection<Long> roleIds);

	List<Permission> listByName(Collection<String> names);

}
