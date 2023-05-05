package net.koodar.forge.admin.application.service;


import net.koodar.forge.admin.application.dto.RoleParamDTO;
import net.koodar.forge.admin.application.dto.RoleQueryDTO;
import net.koodar.forge.common.dto.Response;
import org.springframework.data.domain.Pageable;

/**
 * @author liyc
 */
public interface RoleAppService {

	Response listRole();

	Response pageBy(RoleQueryDTO roleQueryDTO, Pageable pageable);

	Response getRoleInfo(Long id);

	Response addRole(RoleParamDTO roleParamDTO);

	Response updateRole(RoleParamDTO roleParamDTO);

	Response deleteRole(Long id);
}
