package net.koodar.forge.security.userdetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

/**
 * User Detail
 *
 * @author liyc
 */
@Getter
@Setter
@ToString(callSuper = true)
public class AppUserDetails extends User {

	private Long userId;
	private Collection<Long> roleIds;
	private Boolean administratorFlag;
	private String ip;
	private String userAgent;

	public AppUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.administratorFlag = false;
	}

	public AppUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.administratorFlag = false;
	}


}
