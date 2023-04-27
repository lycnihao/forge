package net.koodar.forge.admin.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.koodar.forge.data.jpa.doman.BaseEntity;

import java.util.List;

/**
 * 角色表
 *
 * @author liyc
 */
@Data
@Entity
@Table(name = "t_role")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 角色名
	 */
	@Column(name = "name", length = 50, nullable = false)
	private String name;

	/**
	 * 角色编码
	 */
	@Column(name = "code", length = 50, nullable = false)
	private String code;


	/**
	 * 角色描述
	 */
	@Column(name = "description", length = 127)
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "t_role_permission",
			joinColumns = @JoinColumn(name = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private List<Permission> permissionList;
}
