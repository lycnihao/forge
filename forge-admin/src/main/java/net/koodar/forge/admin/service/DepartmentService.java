package net.koodar.forge.admin.service;

import net.koodar.forge.admin.domain.dto.DepartmentParamDTO;
import net.koodar.forge.common.dto.Response;

/**
 * @author liyc
 */
public interface DepartmentService {

	Response listTree();

	Response addDepartment(DepartmentParamDTO departmentParam);

	Response updateDepartment(DepartmentParamDTO departmentParam);

	Response deleteDepartment(Long id);

}
