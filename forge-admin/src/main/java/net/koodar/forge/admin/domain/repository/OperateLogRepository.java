package net.koodar.forge.admin.domain.repository;

import net.koodar.forge.admin.domain.entity.OperateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 操作日志 Repository
 *
 * @author liyc
 */
public interface OperateLogRepository extends JpaRepository<OperateLog, Long>, JpaSpecificationExecutor<OperateLog> {
}
