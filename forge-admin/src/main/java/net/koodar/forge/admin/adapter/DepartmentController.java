package net.koodar.forge.admin.adapter;

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


	@GetMapping("/department/tree")
	public Response deptTree() {
		return departmentAppService.listTree();
	}

	@PostMapping("/department/add")
	public Response addDepartment(@RequestBody DepartmentParamDTO departmentParam) {
		return departmentAppService.addDepartment(departmentParam);
	}

	@PostMapping("/department/update")
	public Response updDepartment(@RequestBody DepartmentParamDTO departmentParam) {
		return departmentAppService.updateDepartment(departmentParam);
	}

	@PostMapping("/department/delete/{id}")
	public Response delDepartment(@PathVariable("id") Long id) {
		return departmentAppService.deleteDepartment(id);
	}


}
