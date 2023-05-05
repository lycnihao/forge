package net.koodar.forge.admin.adapter;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.application.dto.DepartmentParamDTO;
import net.koodar.forge.admin.application.service.DepartmentAppService;
import net.koodar.forge.common.dto.Response;
import org.springframework.web.bind.annotation.*;


/**
 * 部门接口
 *
 * @author liyc
 */
@RestController
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentAppService departmentAppService;


	/**
	 * 获取Tree结构的部门列表
	 * @return 部门Tree
	 */
	@Operation(summary = "获取部门列表")
	@GetMapping("/department/tree")
	public Response deptTree() {
		return departmentAppService.listTree();
	}

	/**
	 * 添加部门
	 * @param departmentParamDTO 部门参数
	 */
	@Operation(summary = "添加部门")
	@PostMapping("/department/add")
	public Response addDepartment(@RequestBody DepartmentParamDTO departmentParamDTO) {
		return departmentAppService.addDepartment(departmentParamDTO);
	}

	/**
	 * 修改部门
	 * @param departmentParamDTO 部门参数
	 */
	@Operation(summary = "修改部门")
	@PostMapping("/department/update")
	public Response updDepartment(@RequestBody DepartmentParamDTO departmentParamDTO) {
		return departmentAppService.updateDepartment(departmentParamDTO);
	}

	/**
	 * 删除部门
	 * @param id
	 */
	@Operation(summary = "删除部门")
	@PostMapping("/department/delete/{id}")
	public Response delDepartment(@PathVariable("id") Long id) {
		return departmentAppService.deleteDepartment(id);
	}


}
