package net.koodar.forge.admin.adapter;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.application.service.PermissionAppService;
import net.koodar.forge.admin.application.vo.MenuVO;
import net.koodar.forge.common.dto.MultiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Menu controller
 *
 * @author liyc
 */
@RestController
@RequiredArgsConstructor
public class MenusController {

	private final PermissionAppService permissionAppService;

	/**
	 * 获取当前用户菜单，用于前端动态生成菜单
	 *
	 * @return 菜单
	 */
	@Operation(summary = "获取当前用户菜单列表")
	@GetMapping("/menus")
	public MultiResponse<MenuVO> getMenus() {
		return permissionAppService.getMenus();
	}

}
