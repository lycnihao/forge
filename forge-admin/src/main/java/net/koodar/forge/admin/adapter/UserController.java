package net.koodar.forge.admin.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.koodar.forge.admin.application.service.UserAppService;
import net.koodar.forge.common.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller
 *
 * @author liyc
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserAppService userAppService;

	/**
	 * 获取登录用户的用户信息
	 *
	 * @return 用户信息
	 */
	@GetMapping("/user/info")
	public Response getUserInfo() {
		return userAppService.getUserInfo();
	}

	/**
	 * 修改当前用户密码
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @return 操作结果
	 */
	@PostMapping("/user/updatePassword")
	public Response updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
		return userAppService.updatePassword(oldPassword, newPassword);
	}
}
