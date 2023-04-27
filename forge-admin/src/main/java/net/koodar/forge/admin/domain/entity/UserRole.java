package net.koodar.forge.admin.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.koodar.forge.data.jpa.doman.BaseEntity;

/**
 * @author liyc
 */
@Data
@Entity
@Table(name = "t_user_role")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 用户 id
	 */
	@Column(name = "user_id", nullable = false)
	private Long userId;

	/**
	 * 角色 id
	 */
	@Column(name = "role_id", nullable = false)
	private Long roleId;


}
