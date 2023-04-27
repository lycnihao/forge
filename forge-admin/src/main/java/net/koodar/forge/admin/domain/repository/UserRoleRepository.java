package net.koodar.forge.admin.domain.repository;

import net.koodar.forge.admin.domain.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

/**
 * UserRole Repository.
 *
 * @author liyc
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	/**
	 * Find all of userRole by userId
	 * @param userId userId must not be null
	 * @return a list of userRole
	 */
	List<UserRole> findAllByUserId(@NonNull Long userId);

	/**
	 * Find all of userRole by userIds
	 * @param userIds userIds must not be null
	 * @return a list of userRole
	 */
	List<UserRole> findAllByUserIdIn(@NonNull Collection<Long> userIds);

}
