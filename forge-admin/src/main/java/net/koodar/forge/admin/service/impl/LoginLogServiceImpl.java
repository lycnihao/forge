package net.koodar.forge.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.domain.dto.LoginLogQueryDTO;
import net.koodar.forge.admin.service.LoginLogService;
import net.koodar.forge.admin.domain.entity.LoginLog;
import net.koodar.forge.admin.repository.LoginLogRepository;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.dto.SingleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class LoginLogServiceImpl implements LoginLogService {

	private final LoginLogRepository loginLogRepository;

	@Override
	public Response pageBy(LoginLogQueryDTO loginLogQuery, Pageable pageable) {

		Page<LoginLog> logPage = loginLogRepository.findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new LinkedList<>();

			if (StringUtils.hasLength(loginLogQuery.getUsername())) {
				predicates.add(
						criteriaBuilder.like(root.get("userName"), loginLogQuery.getUsername() + "%"));
			}
			if (StringUtils.hasLength(loginLogQuery.getIp())) {
				predicates.add(
						criteriaBuilder.equal(root.get("loginIp"), loginLogQuery.getIp()));
			}
			if (StringUtils.hasLength(loginLogQuery.getStartDate()) && StringUtils.hasLength(loginLogQuery.getEndDate())) {
				Date startDate = DateUtil.parseDate(loginLogQuery.getStartDate());
				Date endDate = DateUtil.parseDate(loginLogQuery.getEndDate());
				predicates.add(criteriaBuilder.between(root.get("createTime"), startDate, endDate));
			}
			if (predicates.size() > 0) {
				query.where(predicates.toArray(new Predicate[0]));
			}
			return query.getRestriction();
		}, pageable);

		return SingleResponse.ok(logPage);
	}
}
