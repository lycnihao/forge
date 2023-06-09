package net.koodar.forge.admin.manager.impl;

import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.entity.Permission;
import net.koodar.forge.admin.domain.entity.RolePermission;
import net.koodar.forge.admin.manager.PermissionManager;
import net.koodar.forge.admin.repository.RolePermissionRepository;
import net.koodar.forge.admin.domain.enums.PermissionTypeEnum;
import net.koodar.forge.admin.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class PermissionManagerImpl implements PermissionManager {

	private final PermissionRepository permissionRepository;
	private final RolePermissionRepository rolePermissionRepository;

	@Override
	public List<Permission> allPermission() {
		return permissionRepository.findAll();
	}

	@Override
	public List<Permission> listFunctionPermission() {
		return permissionRepository.findByType(PermissionTypeEnum.PERMISSIONS.getValue());
	}

	@Override
	public List<Permission> listMenuPermission() {
		return permissionRepository.findByTypeIn(List.of(PermissionTypeEnum.MENU.getValue(), PermissionTypeEnum.CHILD_MENU.getValue()));
	}

	@Override
	public List<Permission> listPermissionByRoleIds(Collection<Long> roleIds) {
		List<RolePermission> rolePermissions = rolePermissionRepository.findAllByRoleIdIn(roleIds);
		List<Long> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
		return permissionRepository.findAllById(permissionIds);
	}

	@Override
	public List<Permission> listMenuPermissionByRoleIds(Collection<Long> roleIds) {
		List<Permission> permissions = this.listPermissionByRoleIds(roleIds);
		return permissions.stream()
				.filter(permission -> !permission.getType()
						.equals(PermissionTypeEnum.PERMISSIONS.getValue()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Permission> listByName(Collection<String> names) {
		return permissionRepository.findByNameIn(names);
	}
}
