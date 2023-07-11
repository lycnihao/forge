package net.koodar.forge.admin.service.impl;

import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.dto.PermissionParamDTO;
import net.koodar.forge.admin.service.PermissionService;
import net.koodar.forge.admin.domain.vo.MenuVO;
import net.koodar.forge.admin.domain.vo.PermissionVo;
import net.koodar.forge.admin.domain.entity.Permission;
import net.koodar.forge.admin.domain.entity.RolePermission;
import net.koodar.forge.admin.repository.PermissionRepository;
import net.koodar.forge.admin.repository.RolePermissionRepository;
import net.koodar.forge.admin.manager.PermissionManager;
import net.koodar.forge.admin.domain.enums.PermissionTypeEnum;
import net.koodar.forge.common.code.UserErrorCode;
import net.koodar.forge.common.dto.MultiResponse;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.exception.BizException;
import net.koodar.forge.common.utils.BeanUtil;
import net.koodar.forge.security.dynamicAuthorization.DynamicSecurityMetadataSource;
import net.koodar.forge.security.userdetails.AppUserDetails;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

	private final PermissionManager permissionManager;
	private final PermissionRepository permissionRepository;
	private final RolePermissionRepository rolePermissionRepository;
	private final ApplicationContext applicationContext;

	@Override
	public List<Permission> listPermission() {
		return permissionManager.listFunctionPermission();
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
			permissions = permissionManager.listMenuPermission();
		} else {
			permissions = permissionManager.listMenuPermissionByRoleIds(userDetails.getRoleIds());
		}
		List<Permission> parentPermissions = permissions
				.stream()
				.filter(permission -> permission.getParentId() == null || permission.getParentId() <= 0)
				.toList();
		return MultiResponse.ok(toMenusTree(parentPermissions, permissions));
	}

	@Override
	public Response listTree() {
		List<Permission> permissions = permissionManager.allPermission();
		List<Permission> parentPermissions = permissions
				.stream()
				.filter(permission -> permission.getParentId() == null || permission.getParentId() <= 0)
				.toList();
		List<PermissionVo> permissionTree = toTree(parentPermissions, permissions);
		return MultiResponse.ok(permissionTree);
	}

	private List<PermissionVo> toTree(List<Permission> permissions, List<Permission> allPermissions) {
		List<PermissionVo> menuVos = new ArrayList<>();
		for (Permission permission : permissions) {
			PermissionVo permissionVo = new PermissionVo();
			permissionVo.setId(permission.getId());
			permissionVo.setName(permission.getName());
			permissionVo.setTitle(permission.getTitle());
			permissionVo.setType(permission.getType());
			permissionVo.setIcon(permission.getIcon());
			permissionVo.setPath(permission.getPath());
			permissionVo.setRedirect(permission.getRedirect());
			permissionVo.setComponent(permission.getComponent());
			permissionVo.setSort(permission.getSort());
			permissionVo.setKeepAlive(permission.getKeepAlive());
			List<Permission> childrenList = allPermissions.stream()
					.filter(p -> p.getParentId() != null && p.getParentId().equals(permission.getId()))
					.collect(Collectors.toList());
			if (!permissions.isEmpty()) {
				List<PermissionVo> children = toTree(childrenList, allPermissions);
				children.forEach(child -> child.setParentName(permissionVo.getName()));
				permissionVo.setChildren(children);
			}
			menuVos.add(permissionVo);
		}
		return menuVos;
	}

	@Override
	public Response addPermission(PermissionParamDTO permissionParamDTO) {
		// 获取父节点id
		Long parentId = getParentId(permissionParamDTO);
		permissionParamDTO.setParentId(parentId);

		Optional<Permission> optionalPermission = permissionRepository.findByName(permissionParamDTO.getName());
		if (optionalPermission.isPresent()) {
			return Response.error(UserErrorCode.ALREADY_EXIST, String.format("当前权限已存在[%s]", permissionParamDTO.getName()));
		}

		Permission permission = BeanUtil.copy(permissionParamDTO, Permission.class);
		permissionRepository.save(permission);

		// 刷新权限
		this.loadDataSource();

		return Response.ok();
	}

	@Override
	public Response updatePermission(PermissionParamDTO permissionParamDTO) {
		Assert.notNull(permissionParamDTO.getId(), "Permission id must not be null");

		// 获取父节点id
		Long parentId = getParentId(permissionParamDTO);
		permissionParamDTO.setParentId(parentId);

		Optional<Permission> dbPermissionOptional = permissionRepository.findByName(permissionParamDTO.getName());
		if (dbPermissionOptional.isPresent()) {
			Permission permission = dbPermissionOptional.get();
			if (!permission.getId().equals(permissionParamDTO.getId())) {
				return Response.error(UserErrorCode.ALREADY_EXIST, String.format("当前权限已存在[%s]", permission.getName()));
			}
		}

		Permission permission = BeanUtil.copy(permissionParamDTO, Permission.class);
		permissionRepository.save(permission);

		// 刷新权限
		this.loadDataSource();

		return Response.ok();
	}

	@Override
	public Response deletePermission(Long permissionId) {
		// 校验删除的资源权限是否被绑定
		List<RolePermission> rolePermissions = rolePermissionRepository.findByPermissionId(permissionId);
		if (!rolePermissions.isEmpty()) {
			throw new BizException("当前菜单权限已存在绑定的角色，请解除绑定后再试");
		}
		permissionRepository.deleteById(permissionId);
		return null;
	}

	/**
	 * 获取父节点Id
	 * @param permissionParamDTO 请求参数，包含菜单类型和父节点key
	 * @return 父节点id
	 */
	private Long getParentId(PermissionParamDTO permissionParamDTO) {
		if (PermissionTypeEnum.MENU.getValue() != permissionParamDTO.getType() &&
				StringUtils.hasLength(permissionParamDTO.getParentKey())) {
			Permission permission = permissionRepository.findByName(permissionParamDTO.getParentKey()).orElseThrow(() -> new BizException(String.format("数据异常 父节点[%s]不存在", permissionParamDTO.getParentKey())));
			return permission.getId();
		}
		return null;
	}

	/**
	 * 刷新权限
	 */
	private void loadDataSource() {
		DynamicSecurityMetadataSource dynamicSecurityMetadataSource = (DynamicSecurityMetadataSource) applicationContext.getBean("dynamicSecurityMetadataSource");
		dynamicSecurityMetadataSource.loadDataSource();
	}

	public List<MenuVO> toMenusTree(List<Permission> permissions, List<Permission> allPermissions) {
		List<MenuVO> menuVos = new ArrayList<>();
		for (Permission permission : permissions) {
			MenuVO menuVo = toMenus(permission);
			List<Permission> childrenList = allPermissions.stream()
					.filter(p -> p.getParentId() != null && p.getParentId().equals(permission.getId()))
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
