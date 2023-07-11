package net.koodar.forge.admin.domain.dto;

import lombok.Getter;
import lombok.Setter;
import net.koodar.forge.common.dto.DTO;

/**
 * @author liyc
 */
@Getter
@Setter
public class PermissionParamDTO extends DTO {

	private Long id;

	private Integer type = 1;

	private Long parentId;

	private String parentKey;
	/**
	 * 目录名称
	 */
	private String title;

	/**
	 * 路由名称
	 */
	private String name;

	/**
	 * 菜单图标
	 */
	private String icon;

	/**
	 * 路由地址
	 */
	private String path;

	/**
	 * 菜单默认路由
	 */
	private String redirect;

	/**
	 * 菜单组件
	 */
	private String component;

	/**
	 * 排序
	 */
	private Integer sort = 0;

	private Boolean keepAlive = true;

}
