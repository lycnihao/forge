package net.koodar.forge.admin.application.dto;

import lombok.Getter;
import lombok.Setter;
import net.koodar.forge.common.dto.DTO;

import java.util.List;

/**
 * @author liyc
 */
@Getter
@Setter
public class AdjDepartmentParamDTO extends DTO {

	private List<Long> userIds;

	private Long departmentId;


}
