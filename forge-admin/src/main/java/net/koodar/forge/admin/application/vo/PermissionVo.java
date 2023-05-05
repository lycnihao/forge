package net.koodar.forge.admin.application.vo;

import lombok.Getter;
import lombok.Setter;
import net.koodar.forge.common.dto.DTO;

import java.util.List;

/**
 * @author liyc
 */
@Setter
@Getter
public class PermissionVo extends DTO {

	private Long id;

	private Long parentId;

	private String parentName;

	private String name;

	private String title;

	private Integer type = 1;

	private String icon;

	private String path;

	private String redirect;

	private String component;

	private Integer sort = 0;

	private Boolean keepAlive = true;


	private List<PermissionVo> children;

}
