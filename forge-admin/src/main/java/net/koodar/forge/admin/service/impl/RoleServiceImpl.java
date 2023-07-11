package net.koodar.forge.admin.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.dto.RoleParamDTO;
import net.koodar.forge.admin.domain.dto.RoleQueryDTO;
import net.koodar.forge.admin.service.RoleService;
import net.koodar.forge.admin.domain.vo.RoleVo;
import net.koodar.forge.admin.domain.entity.Permission;
import net.koodar.forge.admin.domain.entity.Role;
import net.koodar.forge.admin.domain.entity.RolePermission;
import net.koodar.forge.admin.repository.RolePermissionRepository;
import net.koodar.forge.admin.repository.RoleRepository;
import net.koodar.forge.admin.manager.PermissionManager;
import net.koodar.forge.admin.manager.RoleManager;
import net.koodar.forge.common.code.UserErrorCode;
import net.koodar.forge.common.dto.MultiResponse;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.dto.SingleResponse;
import net.koodar.forge.common.utils.BeanUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	private final RoleManager roleManager;

	private final PermissionManager permissionManager;

	private final RolePermissionRepository rolePermissionRepository;

	@Override
	public Response listRole() {
		List<Role> roleList = roleRepository.findAll();
		return MultiResponse.ok(roleList);
	}

	@Override
	public Response pageBy(RoleQueryDTO roleQueryDTO, Pageable pageable) {
		Assert.notNull(roleQueryDTO, "Role query must not be null");
		// Build specification and find all
		Page<Role> rolePage = roleRepository.findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new LinkedList<>();

			if (StringUtils.hasLength(roleQueryDTO.getRoleName())) {
				predicates.add(
						criteriaBuilder.like(root.get("name"), roleQueryDTO.getRoleName() + "%"));
			}
			if (predicates.size() > 0) {
				query.where(predicates.toArray(new Predicate[0]));
			}
			return query.getRestriction();
		}, pageable);

		return SingleResponse.ok(rolePage);
	}

	@Override
	public Response getRoleInfo(Long id) {
		Role role = roleManager.findById(id);
		List<String> permissions = role.getPermissionList().stream().map(Permission::getName).toList();
		RoleVo roleVo = BeanUtil.copy(role, RoleVo.class);
		roleVo.setPermissions(permissions);
		return SingleResponse.ok(roleVo);
	}

	@Override
	public Response addRole(RoleParamDTO roleParamDTO) {
		Optional<Role> optionalRole = roleRepository.findByCode(roleParamDTO.getCode());
		if (optionalRole.isPresent()) {
			return Response.error(UserErrorCode.ALREADY_EXIST, String.format("角色编码 [%s] 已存在，请更换后再试吧。", roleParamDTO.getCode()));
		}
		Role role = BeanUtil.copy(roleParamDTO, new Role());;
		// 保存角色
		roleRepository.save(role);

		// 保存角色和权限信息
		List<Permission> permissions = permissionManager.listByName(roleParamDTO.getPermissions());
		List<RolePermission> rolePermissions = permissions.stream().map(permission -> {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleId(role.getId());
			rolePermission.setPermissionId(permission.getId());
			return rolePermission;
		}).collect(Collectors.toList());
		rolePermissionRepository.saveAll(rolePermissions);
		return Response.ok();
	}

	@Override
	public Response updateRole(RoleParamDTO roleParamDTO) {
		Optional<Role> optionalRole = roleRepository.findById(roleParamDTO.getId());
		if (optionalRole.isEmpty()) {
			return Response.error(UserErrorCode.DATA_NOT_EXIST, String.format("角色id [%s] 不存在。", roleParamDTO.getId()));
		}
		Role role = optionalRole.get();
		Optional<Role> roleOptional = roleRepository.findByCode(role.getCode());
		if (roleOptional.isPresent() && !roleOptional.get().getId().equals(role.getId())) {
			return Response.error(UserErrorCode.ALREADY_EXIST, String.format("角色编码 [%s] 已存在，请更换后再试吧。", roleParamDTO.getCode()));
		}
		BeanUtil.copy(roleParamDTO, role);
		// 保存角色
		roleRepository.save(role);

		List<RolePermission> permissions = rolePermissionRepository.findByRoleId(roleParamDTO.getId());
		rolePermissionRepository.deleteAll(permissions);

		// 保存角色和权限信息
		List<Permission> newPermissions = permissionManager.listByName(roleParamDTO.getPermissions());
		List<RolePermission> rolePermissions = newPermissions.stream().map(permission -> {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleId(role.getId());
			rolePermission.setPermissionId(permission.getId());
			return rolePermission;
		}).collect(Collectors.toList());
		rolePermissionRepository.saveAll(rolePermissions);
		return Response.ok();
	}

	@Override
	public Response deleteRole(Long id) {
		roleManager.deleteRole(id);
		return Response.ok();
	}


}
