package net.koodar.forge.admin.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.dto.AdjDepartmentParamDTO;
import net.koodar.forge.admin.domain.dto.UserParamDTO;
import net.koodar.forge.admin.domain.dto.UserQueryDTO;
import net.koodar.forge.admin.domain.vo.UserVO;
import net.koodar.forge.admin.service.PermissionService;
import net.koodar.forge.admin.service.UserService;
import net.koodar.forge.admin.domain.entity.Permission;
import net.koodar.forge.admin.domain.entity.Role;
import net.koodar.forge.admin.domain.entity.User;
import net.koodar.forge.admin.domain.entity.UserRole;
import net.koodar.forge.admin.repository.RoleRepository;
import net.koodar.forge.admin.repository.UserRepository;
import net.koodar.forge.admin.repository.UserRoleRepository;
import net.koodar.forge.admin.manager.PermissionManager;
import net.koodar.forge.admin.manager.RoleManager;
import net.koodar.forge.admin.manager.UserManager;
import net.koodar.forge.common.code.UserErrorCode;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.dto.SingleResponse;
import net.koodar.forge.security.userdetails.AppUserDetails;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户业务app层
 *
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final RoleRepository roleRepository;
	private final PermissionService permissionService;
	private final PasswordEncoder passwordEncoder;
	private final UserManager userManager;
	private final PermissionManager permissionManager;
	private final RoleManager roleManager;


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

		User user = userManager.findById(userDetails.getUserId());
		UserVO userVo = new UserVO();
		userVo.setUserId(user.getId());
		userVo.setUsername(user.getUsername());
		userVo.setNickname(user.getNickname());
		userVo.setEmail(user.getEmail());
		userVo.setAvatar(user.getAvatar());
		userVo.setDepartmentId(user.getDepartmentId());

		List<Permission> permissions = userDetails.getAdministratorFlag() ?
				permissionManager.allPermission() : permissionManager.listPermissionByRoleIds(userDetails.getRoleIds());
		userVo.setPermissions(permissions
				.stream()
				.map(permission -> Map.of(
						"label", permission.getName(),
						"value", permission.getName()))
				.collect(Collectors.toSet()));
		return SingleResponse.ok(userVo);
	}

	@Override
	public Response getUserInfo(long userId) {
		User user = userManager.findById(userId);

		UserVO userVo = new UserVO();
		userVo.setUserId(user.getId());
		userVo.setUsername(user.getUsername());
		userVo.setNickname(user.getNickname());
		userVo.setEmail(user.getEmail());
		userVo.setAvatar(user.getAvatar());
		userVo.setDepartmentId(user.getDepartmentId());
		Set<Long> roleIds = user.getRoleList().stream().map(Role::getId).collect(Collectors.toSet());
		userVo.setRoleIds(roleIds);
		return SingleResponse.ok(userVo);
	}

	@Override
	public Response updatePassword( String oldPassword, String newPassword) {
		AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = userRepository.findById(userDetails.getUserId()).orElseThrow(() -> new UsernameNotFoundException("用户不存在。"));
		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			return Response.error(UserErrorCode.PARAM_ERROR, "原密码错误，请重新输入。");
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		return Response.ok();
	}

	@Override
	public Response pageBy(UserQueryDTO userQuery, Pageable pageable) {
		Assert.notNull(userQuery, "User query must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		// Build specification and find all
		Page<User> userPage = userRepository.findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new LinkedList<>();

			if (StringUtils.hasLength(userQuery.getUsername())) {
				predicates.add(
						criteriaBuilder.like(root.get("username"), userQuery.getUsername() + "%"));
			}

			if (userQuery.getDepartmentId() != null) {
				predicates.add(
						criteriaBuilder.equal(root.get("departmentId"), userQuery.getDepartmentId()));
			}

			predicates.add(criteriaBuilder.equal(root.get("deletedFlag"), false));

			return query.where(predicates.toArray(new Predicate[0])).getRestriction();
		}, pageable);

		return SingleResponse.ok(userPage.map(user -> {
			UserVO userVo = new UserVO();
			userVo.setUserId(user.getId());
			userVo.setUsername(user.getUsername());
			userVo.setNickname(user.getNickname());
			userVo.setEmail(user.getEmail());
			userVo.setAvatar(user.getAvatar());
			userVo.setCreateDate(user.getCreateTime());

			// Set roles
			userVo.setRoles(user.getRoleList()
					.stream()
					.map(Role::getName)
					.collect(Collectors.toSet()));
			return userVo;
		}));
	}

	@Override
	public Response addUser(UserParamDTO userParamDTO) {
		// 验证用户是否存在
		boolean existsFlag = userManager.checkExistsUsername(userParamDTO.getUsername());
		if (existsFlag) {
			return Response.error(UserErrorCode.PARAM_ERROR, "抱歉，该用户名已经被注册过了");
		}

		User user = new User();
		user.setNickname(userParamDTO.getNickname());
		user.setEmail(userParamDTO.getEmail());
		user.setAvatar(userParamDTO.getAvatar());
		user.setDescription(userParamDTO.getDescription());
		user.setDepartmentId(userParamDTO.getDepartmentId());
		user.setUsername(userParamDTO.getUsername());

		String randomPassword = RandomStringUtils.randomAlphanumeric(8);
		user.setPassword(passwordEncoder.encode(randomPassword));

		// 保存用户信息
		userRepository.save(user);

		// 保存用户角色信息
		List<UserRole> userRoles = userParamDTO.getRoleIds().stream().map(roleId -> {
			UserRole userRole = new UserRole();
			userRole.setUserId(user.getId());
			userRole.setRoleId(roleId);
			return userRole;
		}).collect(Collectors.toList());
		userRoleRepository.saveAll(userRoles);

		return Response.ok();
	}

	@Override
	public Response updateUser(UserParamDTO userParamDTO) {
		User user = userManager.findById(userParamDTO.getUserId());

		user.setNickname(userParamDTO.getNickname());
		user.setEmail(userParamDTO.getEmail());
		user.setAvatar(userParamDTO.getAvatar());
		user.setDescription(userParamDTO.getDescription());
		user.setDepartmentId(userParamDTO.getDepartmentId());
		user.setUsername(userParamDTO.getUsername());

		user.setUsername(userParamDTO.getUsername());
		// 保存用户信息
		userRepository.save(user);

		List<UserRole> userRoles = userRoleRepository.findAllByUserId(userParamDTO.getUserId());
		userRoleRepository.deleteAll(userRoles);

		// 保存用户角色信息
		List<UserRole> newUserRoles = userParamDTO.getRoleIds().stream().map(roleId -> {
			UserRole userRole = new UserRole();
			userRole.setUserId(user.getId());
			userRole.setRoleId(roleId);
			return userRole;
		}).collect(Collectors.toList());
		userRoleRepository.saveAll(newUserRoles);

		return Response.ok();
	}

	@Override
	public Response batchUpdateDeleteFlag(List<Long> userId) {
		List<User> userList = userManager.listByIds(userId);
		if (userList.isEmpty()) {
			return Response.ok();
		}
		for (User user : userList) {
			if (user.getAdministratorFlag()) {
				return Response.error(UserErrorCode.PARAM_ERROR, "超级管理员用户不能删除哦~");
			}
			user.setDeletedFlag(true);
		}
		userManager.batchSave(userList);
		return Response.ok();
	}

	@Override
	public Response disabledUser(long userId) {
		User user = userManager.findById(userId);
		if (user.getAdministratorFlag()) {
			return Response.error(UserErrorCode.PARAM_ERROR, "您不能禁用超级管理员哦~");
		}
		userManager.disabledUser(user);
		return Response.ok();
	}

	@Override
	public Response adjustDepartment(AdjDepartmentParamDTO adjDepartmentParamDTO) {

		List<Long> userIds = adjDepartmentParamDTO.getUserIds();
		for (Long userId : userIds) {
			User user = userManager.findById(userId);
			user.setDepartmentId(adjDepartmentParamDTO.getDepartmentId());
			userManager.save(user);
		}

		return Response.ok();
	}

	@Override
	public Response resetPassword(long userId) {
		String randomPassword = randomPassword();
		User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("用户不存在。"));
		user.setPassword(passwordEncoder.encode(randomPassword));
		userRepository.save(user);
		return SingleResponse.ok(randomPassword);
	}

	private String randomPassword() {
		return RandomStringUtils.randomNumeric(6) + RandomStringUtils.randomAlphabetic(2).toLowerCase();
	}
}
