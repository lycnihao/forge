package net.koodar.forge.admin.application.service;

import net.koodar.forge.admin.application.dto.PermissionParamDTO;
import net.koodar.forge.admin.application.vo.MenuVO;
import net.koodar.forge.admin.domain.entity.Permission;
import net.koodar.forge.common.dto.MultiResponse;
import net.koodar.forge.common.dto.Response;

import java.util.Collection;
import java.util.List;

/**
 * @author liyc
 */
public interface PermissionAppService {

	List<Permission> listPermission();

	List<Permission> listByRoleIds(Collection<Long> roleIds);

	MultiResponse<MenuVO> getMenus();

	Response listTree();

	Response addPermission(PermissionParamDTO permissionParamDTO);

	Response updatePermission(PermissionParamDTO permissionParamDTO);

	Response deletePermission(Long permissionId);

}
