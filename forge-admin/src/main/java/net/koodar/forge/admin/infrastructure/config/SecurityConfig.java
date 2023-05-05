package net.koodar.forge.admin.infrastructure.config;

import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.application.service.RoleAppService;
import net.koodar.forge.admin.application.service.UserAppService;
import net.koodar.forge.admin.domain.entity.Permission;
import net.koodar.forge.admin.domain.entity.Role;
import net.koodar.forge.admin.domain.repository.RoleRepository;
import net.koodar.forge.security.dynamicAuthorization.DynamicSecurityService;
import net.koodar.forge.security.properties.SecurityProperties;
import net.koodar.forge.security.superAdmin.SuperAdminInitializer;
import net.koodar.forge.security.userdetails.AppUserDetailsService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liyc
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserAppService userAppService;
	private final RoleRepository roleRepository;
	private final AppUserDetailsService appUserDetailsService;

	/**
	 * 自定义UserDetailsService
	 */
	@Bean
	UserDetailsService userDetailsService() {
		return appUserDetailsService;
	}

	/**
	 * 初始化超级管理员账户
	 */
	@Bean
	SuperAdminInitializer superAdminInitializer() {
		return new SuperAdminInitializer(new SecurityProperties.Initializer()) {
			@Override
			public boolean isExistsSuperAdministratorUser() {
				return userAppService.isExistsByUsername(getSuperAdminUsername());
			}

			@Override
			public void generateAdministratorUser() {
				userAppService.createAdministrator(getSuperAdminUsername(), getPassword(), SUPER_ROLE_NAME);
			}
		};
	}

	/**
	 * 加载动态权限
	 */
	@Bean
	DynamicSecurityService dynamicSecurityService() {
		List<Role> roles = roleRepository.findAll();

		Map<Long, String> roleCodeByPermissionIdMap = new HashMap<>();
		List<Permission> permissions = new ArrayList<>();
		for (Role role : roles) {
			List<Permission> permissionList = role.getPermissionList();
			permissionList.forEach(permission -> roleCodeByPermissionIdMap.put(permission.getId(), role.getCode()));
			permissions.addAll(permissionList);
		}

		return () -> {
			Map<String, Pair<String, String>> map = new ConcurrentHashMap<>(permissions.size());
			for (Permission permission : permissions) {
				map.put(permission.getPath(),
						Pair.of(permission.getName(), roleCodeByPermissionIdMap.get(permission.getId())));
			}
			return map;
		};
	}
}
