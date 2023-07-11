package net.koodar.forge.admin.service;

import net.koodar.forge.admin.domain.dto.PermissionParamDTO;
import net.koodar.forge.admin.domain.vo.MenuVO;
import net.koodar.forge.admin.domain.entity.Permission;
import net.koodar.forge.common.dto.MultiResponse;
import net.koodar.forge.common.dto.Response;

import java.util.Collection;
import java.util.List;

/**
 * @author liyc
 */
public interface PermissionService {

	List<Permission> listPermission();

	List<Permission> listByRoleIds(Collection<Long> roleIds);

	MultiResponse<MenuVO> getMenus();

	Response listTree();

	Response addPermission(PermissionParamDTO permissionParamDTO);

	Response updatePermission(PermissionParamDTO permissionParamDTO);

	Response deletePermission(Long permissionId);

}
