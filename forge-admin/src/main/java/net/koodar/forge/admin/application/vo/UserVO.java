package net.koodar.forge.admin.application.vo;

import lombok.Data;
import net.koodar.forge.common.dto.DTO;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
public class UserVO extends DTO {

	private Long userId;

	private String username;

	private String nickname;

	private String email;

	private String avatar;

	private LocalDateTime createDate;

	private Set<String> roles;

	private Long departmentId;

	private Set<Long> roleIds;

	private Set<String> roleNames;

	private Set<Map<String, String>> permissions;

}
