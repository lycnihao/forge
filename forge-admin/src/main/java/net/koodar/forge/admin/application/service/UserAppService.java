package net.koodar.forge.admin.application.service;

import net.koodar.forge.admin.application.dto.AdjDepartmentParamDTO;
import net.koodar.forge.admin.application.dto.UserParamDTO;
import net.koodar.forge.admin.application.dto.UserQueryDTO;
import net.koodar.forge.common.dto.Response;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author liyc
 */
public interface UserAppService {

	Boolean isExistsByUsername(String username);

	void createAdministrator(String username, String password, String roleName);

	Response getUserInfo();

	Response getUserInfo(long userId);

	Response updatePassword(String oldPassword, String newPassword);

	Response pageBy(UserQueryDTO userQueryDTO, Pageable pageable);

	Response addUser(UserParamDTO userParamDTO);

	Response updateUser(UserParamDTO userParamDTO);

	Response batchUpdateDeleteFlag(List<Long> userIds);

	Response disabledUser(long userId);

	Response adjustDepartment(AdjDepartmentParamDTO adjDepartmentParamDTO);

	Response resetPassword(long userId);

}
