package net.koodar.forge.admin.domain.dto;

import lombok.Getter;
import lombok.Setter;
import net.koodar.forge.common.dto.DTO;

/**
 * @author liyc
 */
@Getter
@Setter
public class DepartmentParamDTO extends DTO {

	private Long id;

	private Long parentId;

	private String deptName;

	private Integer sort;

}
