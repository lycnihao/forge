package net.koodar.forge.admin.application.dto;

import lombok.Getter;
import lombok.Setter;
import net.koodar.forge.common.dto.DTO;

/**
 * @author liyc
 */
@Setter
@Getter
public class OperateLogQueryDTO extends DTO {

	private String username;

	private String startDate;

	private String endDate;

	private Boolean successFlag;

}
