package net.koodar.forge.admin.application.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.entity.Role;
import net.koodar.forge.admin.domain.entity.User;
import net.koodar.forge.admin.domain.entity.UserRole;
import net.koodar.forge.admin.domain.repository.RoleRepository;
import net.koodar.forge.admin.domain.repository.UserRepository;
import net.koodar.forge.admin.domain.repository.UserRoleRepository;
import net.koodar.forge.common.utils.ServletUtils;
import net.koodar.forge.security.userdetails.AppUserDetails;
import net.koodar.forge.security.userdetails.AppUserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class AppUserDetailsServiceImpl implements AppUserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 1.获取用户信息
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user.getDeletedFlag()) {
				throw new UsernameNotFoundException(String.format("Username [%s] unavailable", username));
			}

			// 2.获取角色信息
			List<Role> roles;
			List<UserRole> userRoles = userRoleRepository.findAllByUserId(user.getId());
			if (!userRoles.isEmpty()) {
				List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
				roles = new ArrayList<>(roleRepository.findAllByIdIn(roleIds));
			} else {
				roles = new ArrayList<>();
			}

			// 3.拼装UserDetails对象
			List<SimpleGrantedAuthority> authorityList = roles
					.stream()
					.map(role -> new SimpleGrantedAuthority(role.getCode()))
					.collect(Collectors.toList());
			boolean userEnabled = user.getStatus() == 1;
			AppUserDetails appUserDetails = new AppUserDetails(user.getUsername(), user.getPassword(), userEnabled, true,true,true, authorityList);
			appUserDetails.setUserId(user.getId());
			appUserDetails.setRoleIds(roles.stream().map(Role::getId).collect(Collectors.toSet()));
			appUserDetails.setAdministratorFlag(user.getAdministratorFlag());
			HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
			appUserDetails.setIp(ServletUtils.getClientIP(request));
			appUserDetails.setUserAgent(ServletUtils.getHeaderIgnoreCase(request, "user-agent"));
			return appUserDetails;
		}
		throw new UsernameNotFoundException(String.format("Username [%s] not found in db", username));
	}
}
