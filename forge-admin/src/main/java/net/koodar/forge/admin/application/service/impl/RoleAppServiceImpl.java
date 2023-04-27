package net.koodar.forge.admin.application.service.impl;

import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.application.service.RoleAppService;
import net.koodar.forge.admin.domain.entity.Role;
import net.koodar.forge.admin.domain.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class RoleAppServiceImpl implements RoleAppService {

	private final RoleRepository roleRepository;

	@Override
	public List<Role> listRole() {
		return roleRepository.findAll();
	}
}
