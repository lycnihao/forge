package net.koodar.forge.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.dto.OperateLogQueryDTO;
import net.koodar.forge.admin.service.OperateLogService;
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
@Tag(name = "OperateLogController", description = "操作日志")
@RestController
@RequiredArgsConstructor
public class OperateLogController {

	private final OperateLogService operateLogService;

	@Operation(summary = "查询操作日志列表")
	@GetMapping("/operateLog/list")
	public Response getUserList(
			@PageableDefault(sort = {"createTime"}, direction = DESC) Pageable pageable,
			OperateLogQueryDTO operateLogQueryDTO) {
		return operateLogService.pageBy(operateLogQueryDTO, pageable);
	}

	@Operation(summary = "查询操作日志详情")
	@GetMapping("/operateLog/detail/{operateLogId}")
	public Response detail(@PathVariable Long operateLogId) {
		return operateLogService.detail(operateLogId);
	}

}
