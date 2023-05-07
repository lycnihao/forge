package net.koodar.forge.admin.adapter;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.application.dto.LoginLogQueryDTO;
import net.koodar.forge.admin.application.service.LoginLogAppService;
import net.koodar.forge.common.dto.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * 登录日志
 *
 * @author liyc
 */
@RestController
@RequiredArgsConstructor
public class LoginLogController {

	private final LoginLogAppService loginLogAppService;

	@Operation(summary = "查询登录日志列表")
	@GetMapping("/loginLog/list")
	public Response getUserList(
			@PageableDefault(sort = {"createTime"}, direction = DESC) Pageable pageable,
			LoginLogQueryDTO loginLogQuery) {
		return loginLogAppService.pageBy(loginLogQuery, pageable);
	}

}
