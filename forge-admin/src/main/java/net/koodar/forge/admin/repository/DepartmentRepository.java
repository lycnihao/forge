package net.koodar.forge.admin.repository;

import net.koodar.forge.admin.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 部门仓储接口
 *
 * @author liyc
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	int countByParentIdAndDeptName(Long parentId, String deptName);

	Department findFirstByParentIdAndDeptName(Long parentId, String deptName);
}
