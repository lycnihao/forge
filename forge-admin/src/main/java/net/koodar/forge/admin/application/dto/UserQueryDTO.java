package net.koodar.forge.admin.application.dto;

import lombok.Getter;
import lombok.Setter;
import net.koodar.forge.common.dto.DTO;

/**
 * @author liyc
 */
@Getter
@Setter
public class UserQueryDTO extends DTO {

	private String username;

	private Long departmentId;

}
