package net.koodar.forge.admin.application.service.impl;

import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.application.dto.DepartmentParamDTO;
import net.koodar.forge.admin.application.service.DepartmentAppService;
import net.koodar.forge.admin.application.vo.DepartmentVo;
import net.koodar.forge.admin.domain.entity.Department;
import net.koodar.forge.admin.domain.repository.DepartmentRepository;
import net.koodar.forge.common.code.UserErrorCode;
import net.koodar.forge.common.dto.MultiResponse;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class DepartmentAppServiceImpl implements DepartmentAppService {

	private final DepartmentRepository departmentRepository;

	/**
	 * 获取Tree结构的部门列表
	 * @return 部门Tree
	 */
	@Override
	public Response listTree() {
		List<Department> departmentList = departmentRepository.findAll();

		List<Department> rootList = departmentList
				.stream()
				.filter(permission -> permission.getParentId() == null)
				.toList();

		List<DepartmentVo> tree = toTree(rootList, departmentList);
		return MultiResponse.ok(tree);
	}

	/**
	 * 添加部门
	 * @param departmentParam 部门参数
	 */
	public Response addDepartment(DepartmentParamDTO departmentParam) {
		int count = departmentRepository.countByParentIdAndDeptName(departmentParam.getParentId(), departmentParam.getDeptName());
		if (count > 0) {
			return Response.error(UserErrorCode.ALREADY_EXIST, String.format("当前部门下已存在名称为[%s]的部门", departmentParam.getDeptName()));
		}

		Department department = BeanUtil.copy(departmentParam, new Department());
		departmentRepository.save(department);
		return Response.ok();
	}

	/**
	 * 修改部门
	 * @param departmentParam 部门参数
	 */
	public Response updateDepartment(DepartmentParamDTO departmentParam) {
		Department dbDepartment = departmentRepository.findFirstByParentIdAndDeptName(departmentParam.getParentId(), departmentParam.getDeptName());
		if (dbDepartment != null) {
			if (!dbDepartment.getId().equals(departmentParam.getId())) {
				return Response.error(UserErrorCode.ALREADY_EXIST, String.format("当前部门下已存在名称为[%s]的部门", departmentParam.getDeptName()));
			}
		}

		Department department = BeanUtil.copy(departmentParam, new Department());
		departmentRepository.save(department);
		return Response.ok();
	}

	/**
	 * 删除部门
	 * @param id
	 */
	public Response deleteDepartment(Long id) {
		departmentRepository.deleteById(id);
		return Response.ok();
	}

	public List<DepartmentVo> toTree(List<Department> departments, List<Department> allDepartments) {
		List<DepartmentVo> menuVos = new ArrayList<>();
		for (Department department : departments) {
			DepartmentVo deptVo = new DepartmentVo();
			BeanUtil.copy(department, deptVo);
			List<Department> childrenList = allDepartments.stream()
					.filter(p -> p.getParentId() != null && p.getParentId().equals(department.getId()))
					.collect(Collectors.toList());
			if (!departments.isEmpty()) {
				List<DepartmentVo> children = toTree(childrenList, allDepartments);
				deptVo.setChildren(children);
			}
			menuVos.add(deptVo);
		}
		return menuVos;
	}
}
