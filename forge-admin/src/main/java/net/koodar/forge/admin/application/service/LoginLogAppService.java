package net.koodar.forge.admin.application.service;

import net.koodar.forge.admin.application.dto.LoginLogQueryDTO;
import net.koodar.forge.common.dto.Response;
import org.springframework.data.domain.Pageable;

/**
 * @author liyc
 */
public interface LoginLogAppService {

	Response pageBy(LoginLogQueryDTO loginLogQuery, Pageable pageable);

}
