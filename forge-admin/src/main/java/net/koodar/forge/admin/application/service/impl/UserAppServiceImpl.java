package net.koodar.forge.admin.application.service.impl;

import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.application.vo.UserVO;
import net.koodar.forge.admin.application.service.PermissionAppService;
import net.koodar.forge.admin.application.service.UserAppService;
import net.koodar.forge.admin.domain.entity.Permission;
import net.koodar.forge.admin.domain.entity.Role;
import net.koodar.forge.admin.domain.entity.User;
import net.koodar.forge.admin.domain.entity.UserRole;
import net.koodar.forge.admin.domain.repository.RoleRepository;
import net.koodar.forge.admin.domain.repository.UserRepository;
import net.koodar.forge.admin.domain.repository.UserRoleRepository;
import net.koodar.forge.admin.domain.service.PermissionService;
import net.koodar.forge.admin.domain.service.UserService;
import net.koodar.forge.common.code.UserErrorCode;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.dto.SingleResponse;
import net.koodar.forge.common.exception.BizException;
import net.koodar.forge.security.userdetails.AppUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户业务app层
 *
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class UserAppServiceImpl implements UserAppService {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final RoleRepository roleRepository;
	private final PermissionAppService permissionAppService;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final PermissionService permissionService;



	@Override
	public Boolean isExistsByUsername(String username) {
		return userRepository.findByUsername(username).isPresent();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createAdministrator(String username, String password, String roleName) {
		User user = new User();
		user.setUsername(username);
		user.setNickname(username);
		user.setDescription("超级管理员用户");
		user.setPassword(passwordEncoder.encode(password));
		user.setAdministratorFlag(true);
		userRepository.save(user);

		Role role = new Role();
		role.setName(roleName);
		role.setCode(roleName);
		role.setDescription("超级管理员");
		roleRepository.save(role);

		UserRole userRole = new UserRole();
		userRole.setUserId(user.getId());
		userRole.setRoleId(role.getId());
		userRoleRepository.save(userRole);
	}

	@Override
	public Response getUserInfo() {
		AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Optional<User> optionalUser = userRepository.findById(userDetails.getUserId());
		if (optionalUser.isEmpty()) {
			throw new UsernameNotFoundException(String.format("UserId [%s] not found in db", userDetails.getUserId()));
		}

		User user = optionalUser.get();
		UserVO userVo = new UserVO();
		userVo.setUserId(user.getId());
		userVo.setUsername(user.getUsername());
		userVo.setNickname(user.getNickname());
		userVo.setEmail(user.getEmail());
		userVo.setAvatar(user.getAvatar());
		userVo.setDepartmentId(user.getDepartmentId());

		List<Permission> permissions = userDetails.getAdministratorFlag() ?
				permissionService.allPermission() : permissionService.listPermissionByRoleIds(userDetails.getRoleIds());
		userVo.setPermissions(permissions
				.stream()
				.map(permission -> Map.of(
						"label", permission.getName(),
						"value", permission.getName()))
				.collect(Collectors.toSet()));
		return SingleResponse.ok(userVo);
	}

	@Override
	public Response updatePassword( String oldPassword, String newPassword) {
		AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			userService.updatePassword(userDetails.getUserId(), oldPassword, newPassword, passwordEncoder);
			return Response.ok();
		} catch (BizException e) {
			return Response.error(UserErrorCode.PARAM_ERROR, e.getMessage());
		}
	}
}
