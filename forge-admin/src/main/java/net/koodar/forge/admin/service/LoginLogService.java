package net.koodar.forge.admin.service;

import net.koodar.forge.admin.domain.dto.LoginLogQueryDTO;
import net.koodar.forge.common.dto.Response;
import org.springframework.data.domain.Pageable;

/**
 * @author liyc
 */
public interface LoginLogService {

	Response pageBy(LoginLogQueryDTO loginLogQuery, Pageable pageable);

}
