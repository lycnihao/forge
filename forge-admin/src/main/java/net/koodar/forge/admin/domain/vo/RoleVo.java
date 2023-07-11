package net.koodar.forge.admin.domain.vo;

import lombok.Getter;
import lombok.Setter;
import net.koodar.forge.common.dto.DTO;

import java.util.List;

/**
 * @author liyc
 */
@Setter
@Getter
public class RoleVo extends DTO {

	private Long id;

	private String name;

	private String code;

	private String description;

	private Integer status;

	private List<String> permissions;

}
