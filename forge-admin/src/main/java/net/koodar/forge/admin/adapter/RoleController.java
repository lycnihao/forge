package net.koodar.forge.admin.adapter;

import lombok.AllArgsConstructor;
import net.koodar.forge.admin.application.dto.RoleParamDTO;
import net.koodar.forge.admin.application.dto.RoleQueryDTO;
import net.koodar.forge.admin.application.service.RoleAppService;
import net.koodar.forge.common.dto.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Role controller.
 *
 * @author liyc
 */
@RestController
@AllArgsConstructor
public class RoleController {

	private final RoleAppService roleAppService;

	/**
	 * 获取所有角色列表
	 * @return 角色列表
	 */
	@GetMapping("/role/listRole")
	public Response listRole() {
		return roleAppService.listRole();
	}

	/**
	 * 查询角色列表
	 * @param pageable 分页参数
	 * @param roleQuery 查询条件
	 * @return 角色列表
	 */
	@GetMapping("/role/list")
	public Response getRoleList(
			@PageableDefault(sort = {"createTime"}, direction = DESC) Pageable pageable,
			RoleQueryDTO roleQuery) {
		return roleAppService.pageBy(roleQuery, pageable);
	}

	/**
	 * 获取角色信息
	 * @param id 角色id
	 * @return 角色信息
	 */
	@GetMapping("/role/info")
	public Response getRoleInfo(@RequestParam Long id) {
		return roleAppService.getRoleInfo(id);
	}

	/**
	 * 添加角色
	 *
	 * @param roleParamDTO 角色参数
	 * @return 操作结果
	 */
	@PostMapping("/role/add")
	public Response addRole(@RequestBody RoleParamDTO roleParamDTO) {
		return roleAppService.addRole(roleParamDTO);
	}

	/**
	 * 更新角色
	 *
	 * @param roleParamDTO 角色参数
	 * @return 操作结果
	 */
	@PostMapping("/role/update")
	public Response updateRole(@RequestBody RoleParamDTO roleParamDTO) {
		return roleAppService.updateRole(roleParamDTO);
	}

	/**
	 * 删除角色
	 *
	 * @param roleId 角色Id
	 * @return 操作结果
	 */
	@PostMapping("/role/delete")
	public Response deleteRole(@RequestParam Long roleId) {
		return roleAppService.deleteRole(roleId);
	}

}
