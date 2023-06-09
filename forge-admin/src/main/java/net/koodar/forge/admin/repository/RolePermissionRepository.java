package net.koodar.forge.admin.repository;

import net.koodar.forge.admin.domain.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * RolePermission repository.
 *
 * @author liyc
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

	List<RolePermission> findByPermissionId(Long permissionId);

	List<RolePermission> findByRoleId(Long roleId);

	List<RolePermission> findAllByRoleIdIn(Collection<Long> roleIds);

	List<RolePermission> findByPermissionIdIn(Collection<Long> permissionIds);

}
