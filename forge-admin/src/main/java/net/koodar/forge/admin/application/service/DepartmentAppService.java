package net.koodar.forge.admin.application.service;

import net.koodar.forge.admin.application.dto.DepartmentParamDTO;
import net.koodar.forge.common.dto.Response;

/**
 * @author liyc
 */
public interface DepartmentAppService {

	Response listTree();

	Response addDepartment(DepartmentParamDTO departmentParam);

	Response updateDepartment(DepartmentParamDTO departmentParam);

	Response deleteDepartment(Long id);

}
