package net.koodar.forge.admin.adapter;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.application.dto.OperateLogQueryDTO;
import net.koodar.forge.admin.application.service.OperateLogAppService;
import net.koodar.forge.common.dto.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * 操作日志接口
 *
 * @author liyc
 */
@RestController
@RequiredArgsConstructor
public class OperateLogController {

	private final OperateLogAppService operateLogAppService;

	@Operation(summary = "查询操作日志列表")
	@GetMapping("/operateLog/list")
	public Response getUserList(
			@PageableDefault(sort = {"createTime"}, direction = DESC) Pageable pageable,
			OperateLogQueryDTO operateLogQueryDTO) {
		return operateLogAppService.pageBy(operateLogQueryDTO, pageable);
	}

	@Operation(summary = "查询操作日志详情")
	@GetMapping("/operateLog/detail/{operateLogId}")
	public Response detail(@PathVariable Long operateLogId) {
		return operateLogAppService.detail(operateLogId);
	}

}
