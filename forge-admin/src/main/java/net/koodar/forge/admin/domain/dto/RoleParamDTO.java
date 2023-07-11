package net.koodar.forge.admin.domain.dto;

import lombok.Getter;
import lombok.Setter;
import net.koodar.forge.common.dto.DTO;

import java.util.List;

/**
 * @author liyc
 */
@Getter
@Setter
public class RoleParamDTO extends DTO {

	private Long id;
	private String name;
	private String code;
	private String description;
	private List<String> permissions;

}
