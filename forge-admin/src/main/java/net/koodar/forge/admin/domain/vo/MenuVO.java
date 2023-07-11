package net.koodar.forge.admin.domain.vo;

import lombok.Data;
import net.koodar.forge.common.dto.DTO;

import java.util.Collection;
import java.util.Set;

@Data
public class MenuVO extends DTO {

	/**
	 * 资源权限名称
	 */
	private String name;

	/**
	 * 资源权限显示名称
	 */
	private String title;

	/**
	 * 路由地址
	 */
	private String path;

	/**
	 * 重定向地址
	 */
	private String redirect;

	/**
	 * 页面组件
	 */
	private String component;

	/**
	 * meta属性，页面标题, 菜单图标等
	 */
	private PermissionMetaVO meta;

	/**
	 * 下级权限
	 */
	private Collection<MenuVO> children;

	@Data
	public static class PermissionMetaVO {
		/**
		 * 菜单名称
		 */
		private String title;

		/**
		 * 图标
		 */
		private String icon;

		/**
		 * 排序编号
		 */
		private Integer sort;

		/**
		 * 缓存该路由
		 */
		private Boolean keepAlive;

		/**
		 * 下级权限
		 */
		private Set<String> permissions;
	}

}
