package net.koodar.forge.admin.adapter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.koodar.forge.admin.application.dto.AdjDepartmentParamDTO;
import net.koodar.forge.admin.application.dto.UserParamDTO;
import net.koodar.forge.admin.application.dto.UserQueryDTO;
import net.koodar.forge.admin.application.service.UserAppService;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.module.operatelog.OperateLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * 用户管理接口
 *
 * @author liyc
 */
@OperateLog
@Tag(name = "UserController", description = "系统管理-用户管理")
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
	@Operation(summary = "获取登录用户的用户信息")
	@GetMapping("/user/info")
	public Response getUserInfo() {
		return userAppService.getUserInfo();
	}

	/**
	 * 通过userId获取用户信息
	 *
	 * @param userId 用户id
	 * @return 用户信息
	 */
	@Operation(summary = "通过userId获取用户信息")
	@GetMapping(value="/user/info/{userId}")
	public Response getUserInfoById(@PathVariable("userId") Long userId) {
		return userAppService.getUserInfo(userId);
	}

	/**
	 * 修改当前用户密码
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @return 操作结果
	 */
	@Operation(summary = "修改当前用户密码")
	@PostMapping("/user/updatePassword")
	public Response updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
		return userAppService.updatePassword(oldPassword, newPassword);
	}

	/**
	 * 重置用户密码
	 * @param userId 用户id
	 * @return 操作结果
	 */
	@Operation(summary = "重置用户密码")
	@PostMapping("/user/update/password/password/reset/{userId}")
	public Response resetPassword(@PathVariable("userId") Long userId) {
		return userAppService.resetPassword(userId);
	}

	/**
	 * 获取用户列表
	 *
	 * @param pageable 分页参数
	 * @param userQuery 查询条件
	 * @return 用户列表
	 */
	@Operation(summary = "获取用户列表")
	@GetMapping("/user/list")
	public Response getUserList(
			@PageableDefault(sort = {"createTime"}, direction = DESC) Pageable pageable,
			UserQueryDTO userQuery) {
		return userAppService.pageBy(userQuery, pageable);
	}

	/**
	 * 添加用户
	 *
	 * @param userParamDTO 用户参数
	 * @return 操作结果
	 */
	@Operation(summary = "添加用户")
	@PostMapping("/user/add")
	public Response addUser(@RequestBody UserParamDTO userParamDTO) {
		return userAppService.addUser(userParamDTO);
	}

	/**
	 * 更新用户
	 *
	 * @param userParamDTO 用户参数
	 * @return 操作结果
	 */
	@Operation(summary = "更新用户")
	@PostMapping("/user/update")
	public Response updateUser(@RequestBody UserParamDTO userParamDTO) {
		return userAppService.updateUser(userParamDTO);
	}

	/**
	 * 删除用户
	 *
	 * @param userId 用户Id
	 * @return 操作结果
	 */
	@Operation(summary = "删除用户")
	@PostMapping("/user/delete")
	public Response deletedUser(@RequestParam Long userId) {
		return userAppService.deleteUser(userId);
	}

	/**
	 * 调整当前用户部门
	 * @param adjDepartmentParamDTO 请求参数
	 * @return 操作结果
	 */
	@Operation(summary = "调整当前用户部门")
	@PostMapping("/user/adjustDepartment")
	public Response updateDepartment(@RequestBody AdjDepartmentParamDTO adjDepartmentParamDTO) {
		return userAppService.adjustDepartment(adjDepartmentParamDTO);
	}


}
