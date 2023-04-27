package net.koodar.forge.admin.application.service.impl;

import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.application.service.PermissionAppService;
import net.koodar.forge.admin.application.vo.MenuVO;
import net.koodar.forge.admin.domain.entity.Permission;
import net.koodar.forge.admin.domain.entity.RolePermission;
import net.koodar.forge.admin.domain.repository.PermissionRepository;
import net.koodar.forge.admin.domain.repository.RolePermissionRepository;
import net.koodar.forge.admin.domain.service.PermissionService;
import net.koodar.forge.common.dto.MultiResponse;
import net.koodar.forge.security.userdetails.AppUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class PermissionAppServiceImpl implements PermissionAppService {

	private final PermissionService permissionService;
	private final PermissionRepository permissionRepository;
	private final RolePermissionRepository rolePermissionRepository;


	@Override
	public List<Permission> listPermission() {
		return permissionService.listFunctionPermission();
	}

	@Override
	public List<Permission> listByRoleIds(Collection<Long> roleIds) {
		List<RolePermission> rolePermissions = rolePermissionRepository.findAllByRoleIdIn(roleIds);
		List<Long> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
		return permissionRepository.findAllById(permissionIds);
	}

	/**
	 * 获取当前用户菜单树
	 * @return 菜单tree
	 */
	@Override
	public MultiResponse<MenuVO> getMenus() {
		AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 获取资源权限
		List<Permission> permissions;
		if (userDetails.getAdministratorFlag()) {
			permissions = permissionService.listMenuPermission();
		} else {
			permissions = permissionService.listMenuPermissionByRoleIds(userDetails.getRoleIds());
		}
		List<Permission> parentPermissions = permissions
				.stream()
				.filter(permission -> permission.getParentId() != null && permission.getParentId() <= 0)
				.toList();
		return MultiResponse.ok(toMenusTree(parentPermissions, permissions));
	}

	public List<MenuVO> toMenusTree(List<Permission> permissions, List<Permission> allPermissions) {
		List<MenuVO> menuVos = new ArrayList<>();
		for (Permission permission : permissions) {
			MenuVO menuVo = toMenus(permission);
			List<Permission> childrenList = allPermissions.stream()
					.filter(p -> p.getParentId().equals(permission.getId()))
					.collect(Collectors.toList());
			if (!permissions.isEmpty()) {
				List<MenuVO> childrenMenus = toMenusTree(childrenList, allPermissions);
				menuVo.setChildren(childrenMenus);
			}
			menuVos.add(menuVo);
		}
		return menuVos;
	}

	private MenuVO toMenus(Permission permission) {
		MenuVO menuVo = new MenuVO();
		menuVo.setName(permission.getName());
		menuVo.setTitle(permission.getTitle());
		menuVo.setPath(permission.getPath());
		menuVo.setComponent(permission.getComponent());
		MenuVO.PermissionMetaVO permissionMetaVo = new MenuVO.PermissionMetaVO();
		permissionMetaVo.setTitle(permission.getTitle());
		permissionMetaVo.setIcon(permission.getIcon());
		permissionMetaVo.setSort(permission.getSort());
		permissionMetaVo.setKeepAlive(permission.getKeepAlive());
		menuVo.setMeta(permissionMetaVo);
		menuVo.setChildren(new ArrayList<>());
		return menuVo;
	}
}
