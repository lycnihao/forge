package net.koodar.forge.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.dto.DepartmentParamDTO;
import net.koodar.forge.admin.service.DepartmentService;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.module.operatelog.OperateLog;
import org.springframework.web.bind.annotation.*;


/**
 * 部门接口
 *
 * @author liyc
 */
@OperateLog
@Tag(name = "DepartmentController", description = "系统管理-部门")
@RestController
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService departmentService;


	/**
	 * 获取Tree结构的部门列表
	 * @return 部门Tree
	 */
	@Operation(summary = "获取部门列表")
	@GetMapping("/department/tree")
	public Response deptTree() {
		return departmentService.listTree();
	}

	/**
	 * 添加部门
	 * @param departmentParamDTO 部门参数
	 */
	@Operation(summary = "添加部门")
	@PostMapping("/department/add")
	public Response addDepartment(@RequestBody DepartmentParamDTO departmentParamDTO) {
		return departmentService.addDepartment(departmentParamDTO);
	}

	/**
	 * 修改部门
	 * @param departmentParamDTO 部门参数
	 */
	@Operation(summary = "修改部门")
	@PostMapping("/department/update")
	public Response updDepartment(@RequestBody DepartmentParamDTO departmentParamDTO) {
		return departmentService.updateDepartment(departmentParamDTO);
	}

	/**
	 * 删除部门
	 * @param id
	 */
	@Operation(summary = "删除部门")
	@PostMapping("/department/delete/{id}")
	public Response delDepartment(@PathVariable("id") Long id) {
		return departmentService.deleteDepartment(id);
	}


}
