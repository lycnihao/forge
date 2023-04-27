package net.koodar.forge.admin.application.vo;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class UserVO {

	private Long userId;

	private String username;

	private String nickname;

	private String email;

	private String avatar;

	private Set<String> roles;

	private Long departmentId;

	private Set<Long> roleIds;

	private Set<Map<String, String>> permissions;

}
