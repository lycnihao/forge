package net.koodar.forge.admin.adapter;

import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.application.dto.PermissionParamDTO;
import net.koodar.forge.admin.application.service.PermissionAppService;
import net.koodar.forge.common.dto.Response;
import org.springframework.web.bind.annotation.*;

/**
 * @author liyc
 */
@RestController
@RequiredArgsConstructor
public class PermissionController {

	private final PermissionAppService permissionAppService;

	/**
	 * 获取所有权限
	 * @return 权限tree
	 */
	@GetMapping("/permission/getAllPermissions")
	public Response listAllPermission() {
		return permissionAppService.listTree();
	}

	/**
	 * 添加权限
	 *
	 * @param permissionParamDTO 权限参数
	 * @return 操作结果
	 */
	@PostMapping("/permission/add")
	public Response addPermission(@RequestBody PermissionParamDTO permissionParamDTO) {
		return permissionAppService.addPermission(permissionParamDTO);
	}

	/**
	 * 更新权限
	 *
	 * @param permissionParamDTO 权限参数
	 * @return 操作结果
	 */
	@PostMapping("/permission/update")
	public Response updatePermission(@RequestBody PermissionParamDTO permissionParamDTO) {
		return permissionAppService.updatePermission(permissionParamDTO);
	}

	/**
	 * 删除权限
	 *
	 * @param permissionId 权限Id
	 * @return 操作结果
	 */
	@PostMapping("/permission/delete")
	public Response deletedPermission(@RequestParam Long permissionId) {
		return permissionAppService.deletePermission(permissionId);
	}

}