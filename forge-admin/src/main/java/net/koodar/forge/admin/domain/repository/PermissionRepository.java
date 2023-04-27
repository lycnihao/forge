package net.koodar.forge.admin.domain.repository;

import net.koodar.forge.admin.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Permission repository.
 *
 * @author liyc
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	Optional<Permission> findByName(String name);

	List<Permission> findByNameIn(Collection<String> names);

	List<Permission> findByType(Integer type);

	List<Permission> findByTypeIn(List<Integer> typs);

}
