package net.koodar.forge.admin.application.service;

import net.koodar.forge.admin.application.dto.OperateLogQueryDTO;
import net.koodar.forge.common.dto.Response;
import org.springframework.data.domain.Pageable;

/**
 * @author liyc
 */
public interface OperateLogAppService {

	Response pageBy(OperateLogQueryDTO operateLogQuery, Pageable pageable);

	Response detail(Long operateLogId);

}
