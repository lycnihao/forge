package net.koodar.forge.data.jpa.doman;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Base entity.
 *
 * @author liyc
 */
@Data
@ToString
@MappedSuperclass
@EqualsAndHashCode
public class BaseEntity {

	/**
	 * Create time.
	 */
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createTime;

	/**
	 * Update time.
	 */
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime updateTime;

	@PrePersist
	protected void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		if (createTime == null) {
			createTime = now;
		}

		if (updateTime == null) {
			updateTime = now;
		}
	}

	@PreUpdate
	protected void preUpdate() {
		updateTime = LocalDateTime.now();
	}

	@PreRemove
	protected void preRemove() {
		updateTime = LocalDateTime.now();
	}

}
