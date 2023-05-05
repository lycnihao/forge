package net.koodar.forge.admin.domain.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.koodar.forge.data.jpa.doman.BaseEntity;

/**
 * 部门表
 *
 * @author liyc
 */
@Data
@Entity
@Table(name = "t_dept")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Department extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 上级部门
	 */
	@Column(name = "parent_id")
	private Long parentId;

	/**
	 * 部门名称
	 */
	@Column(name = "dept_name")
	private String deptName;

	/**
	 * 显示排序
	 */
	@Column(name = "`sort`", nullable = false)
	private Integer sort = 0;

}