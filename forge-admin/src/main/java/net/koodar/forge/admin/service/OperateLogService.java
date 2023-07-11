package net.koodar.forge.admin.service;

import net.koodar.forge.admin.domain.dto.OperateLogQueryDTO;
import net.koodar.forge.common.dto.Response;
import org.springframework.data.domain.Pageable;

/**
 * @author liyc
 */
public interface OperateLogService {

	Response pageBy(OperateLogQueryDTO operateLogQuery, Pageable pageable);

	Response detail(Long operateLogId);

}
