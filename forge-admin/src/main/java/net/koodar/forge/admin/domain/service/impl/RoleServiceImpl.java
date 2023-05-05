package net.koodar.forge.admin.domain.service.impl;

import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.entity.Role;
import net.koodar.forge.admin.domain.repository.RoleRepository;
import net.koodar.forge.admin.domain.service.RoleService;
import net.koodar.forge.common.exception.BizException;
import org.springframework.stereotype.Service;


/**
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	@Override
	public void deleteRole(Long id) {
		roleRepository.deleteById(id);
	}

	@Override
	public Role findById(Long id) {
		return roleRepository.findById(id).orElseThrow(() -> new BizException(String.format("角色[%s]不存在", id)));
	}
}
