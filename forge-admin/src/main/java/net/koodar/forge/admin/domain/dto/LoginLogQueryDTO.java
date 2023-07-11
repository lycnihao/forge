package net.koodar.forge.admin.domain.dto;

import lombok.Getter;
import lombok.Setter;
import net.koodar.forge.common.dto.DTO;

/**
 * @author liyc
 */
@Getter
@Setter
public class LoginLogQueryDTO extends DTO {

	private String startDate;

	private String endDate;

	private String username;

	private String ip;

}
