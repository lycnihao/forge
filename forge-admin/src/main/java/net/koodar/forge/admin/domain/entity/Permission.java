package net.koodar.forge.admin.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.koodar.forge.data.jpa.doman.BaseEntity;

/**
 * 资源权限表
 *
 * @author liyc
 */
@Data
@Entity
@Table(name = "t_permission")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 父节点 id
	 */
	@Column(name = "parent_id")
	private Long parentId;

	/**
	 * 资源权限名称
	 */
	@Column(name = "name", length = 127, nullable = false)
	private String name;

	/**
	 * 资源权限显示名称
	 */
	@Column(name = "title", length = 127, nullable = false)
	private String title;

	/**
	 * 菜单类型
	 * 1:目录; 2:菜单; 3:按钮
	 */
	@Column(name = "type", length = 127, nullable = false)
	private Integer type = 1;

	/**
	 * 图标
	 */
	@Column(name = "icon", length = 32)
	private String icon;

	/**
	 * 路由地址
	 */
	@Column(name = "path", length = 2048, nullable = false)
	private String path;

	/**
	 * 重定向地址
	 */
	@Column(name = "redirect", length = 2048)
	private String redirect;

	/**
	 * 组件路径
	 */
	@Column(name = "component", length = 127)
	private String component;

	/**
	 * 显示排序
	 */
	@Column(name = "`sort`", nullable = false)
	private Integer sort = 0;

	/**
	 * 是否缓存
	 */
	@Column(name = "keep_alive", nullable = false)
	private Boolean keepAlive = true;

}
