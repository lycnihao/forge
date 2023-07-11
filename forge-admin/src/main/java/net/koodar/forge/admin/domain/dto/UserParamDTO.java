package net.koodar.forge.admin.domain.dto;

import lombok.Getter;
import lombok.Setter;
import net.koodar.forge.common.dto.DTO;

import java.util.Set;

/**
 * @author liyc
 */
@Getter
@Setter
public class UserParamDTO extends DTO {

	private Long userId;

	private String username;

	private String nickname;

	private String email;

	private String avatar;

	private String description;

	private Long departmentId;

	private Set<Long> roleIds;

}
