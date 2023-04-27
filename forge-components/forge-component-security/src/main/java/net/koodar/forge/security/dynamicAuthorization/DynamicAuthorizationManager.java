package net.koodar.forge.security.dynamicAuthorization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.koodar.forge.security.userdetails.AppUserDetails;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 动态权限授权管理器
 *
 * @author liyc
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicAuthorizationManager<T> implements AuthorizationManager<T> {

	private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

	private final DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, T object) {
		// Determines if the current user is authorized by evaluating if the
		boolean granted = isGranted(authentication.get());
		if (!granted) {
			return new AuthorizationDecision(false);
		}

		Object principal = authentication.get().getPrincipal();
		if (!principal.getClass().equals(AppUserDetails.class)) {
			return new AuthorizationDecision(false);
		}

		// 超级管理员
		if (((AppUserDetails) principal).getAdministratorFlag()) {
			return new AuthorizationDecision(true);
		}

		Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
		Set<String> authority = authorities
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toSet());

		log.debug("username [{}] hav roles:[{}]", authentication.get().getName(), authority);

		RequestAuthorizationContext requestAuthorizationContext = (RequestAuthorizationContext)object;
		String servletPath = requestAuthorizationContext.getRequest().getRequestURI();
		log.debug("access url:{}", servletPath);

		if (dynamicSecurityMetadataSource.checkPermissionUrl(servletPath)) {
			Collection<Pair<String, String>> configAttributes = dynamicSecurityMetadataSource.getAttributes(requestAuthorizationContext);
			for (Pair<String, String> configAttribute : configAttributes) {
				//将访问所需资源或用户拥有资源进行比对
				String needAuthority = configAttribute.getValue();
				if (!authority.contains(needAuthority)) {
					return new AuthorizationDecision(false);
				}
			}
		}
		return new AuthorizationDecision(true);
	}

	private boolean isGranted(Authentication authentication) {
		return authentication != null && isNotAnonymous(authentication) && authentication.isAuthenticated();
	}

	private boolean isNotAnonymous(Authentication authentication) {
		return !this.trustResolver.isAnonymous(authentication);
	}

}
