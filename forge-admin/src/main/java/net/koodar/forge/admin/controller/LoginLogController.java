package net.koodar.forge.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.dto.LoginLogQueryDTO;
import net.koodar.forge.admin.service.LoginLogService;
import net.koodar.forge.common.dto.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * 登录日志接口
 *
 * @author liyc
 */
@Tag(name = "LoginLogController", description = "系统管理-登录日志")
@RestController
@RequiredArgsConstructor
public class LoginLogController {

	private final LoginLogService loginLogService;

	@Operation(summary = "查询登录日志列表")
	@GetMapping("/loginLog/list")
	public Response getUserList(
			@PageableDefault(sort = {"createTime"}, direction = DESC) Pageable pageable,
			LoginLogQueryDTO loginLogQuery) {
		return loginLogService.pageBy(loginLogQuery, pageable);
	}

}
