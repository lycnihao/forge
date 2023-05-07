package net.koodar.forge.admin.adapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.application.dto.PermissionParamDTO;
import net.koodar.forge.admin.application.service.PermissionAppService;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.module.operatelog.OperateLog;
import org.springframework.web.bind.annotation.*;

/**
 * 权限菜单接口
 *
 * @author liyc
 */
@OperateLog
@Tag(name = "PermissionController", description = "系统管理-权限菜单")
@RestController
@RequiredArgsConstructor
public class PermissionController {

	private final PermissionAppService permissionAppService;

	/**
	 * 获取所有权限
	 * @return 权限tree
	 */
	@Operation(summary = "获取所有权限")
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
	@Operation(summary = "添加权限")
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
	@Operation(summary = "更新权限")
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
	@Operation(summary = "删除权限")
	@PostMapping("/permission/delete")
	public Response deletedPermission(@RequestParam Long permissionId) {
		return permissionAppService.deletePermission(permissionId);
	}

}
