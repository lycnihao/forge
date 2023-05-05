package net.koodar.forge.admin.application.vo;

import lombok.Data;

import java.util.List;

/**
 * Dept Tree
 *
 * @author liyc
 */
@Data
public class DepartmentVo {

	private Long id;

	private Long parentId;

	private String deptName;

	private Integer sort = 0;

	private List<DepartmentVo> children;

}
