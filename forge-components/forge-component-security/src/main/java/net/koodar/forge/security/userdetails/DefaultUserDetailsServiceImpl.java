package net.koodar.forge.security.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultUserDetailsServiceImpl implements AppUserDetailsService {


	private final Map<String, AppUserDetails> userDetailsMap = new ConcurrentHashMap<>();

	public DefaultUserDetailsServiceImpl(PasswordEncoder passwordEncoder) {

		String password = passwordEncoder.encode("123456");
		userDetailsMap.put("admin",
				new AppUserDetails("admin", password, Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))));
		userDetailsMap.put("user",
				new AppUserDetails("user", password, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))));

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDetailsMap.get(username);
	}
}
