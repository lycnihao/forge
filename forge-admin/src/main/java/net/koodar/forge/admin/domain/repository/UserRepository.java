package net.koodar.forge.admin.domain.repository;

import net.koodar.forge.admin.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * User repository.
 *
 * @author liyc
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	/**
	 * Gets user by username.
	 * @param username username must not be blank
	 * @return an optional user
	 */
	@NonNull
	Optional<User> findByUsername(@NonNull String username);

	/**
	 * Gets user by email.
	 *
	 * @param email email must not be blank
	 * @return an optional user
	 */
	@NonNull
	Optional<User> findByEmail(@NonNull String email);

	List<User> findByIdIn(List<Long> ids);
}
