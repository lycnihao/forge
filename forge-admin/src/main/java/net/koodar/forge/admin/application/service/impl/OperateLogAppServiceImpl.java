package net.koodar.forge.admin.application.service.impl;

import cn.hutool.core.date.DateUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import net.koodar.forge.admin.application.dto.OperateLogQueryDTO;
import net.koodar.forge.admin.application.service.OperateLogAppService;
import net.koodar.forge.admin.domain.entity.OperateLog;
import net.koodar.forge.admin.domain.repository.OperateLogRepository;
import net.koodar.forge.common.dto.Response;
import net.koodar.forge.common.dto.SingleResponse;
import net.koodar.forge.common.exception.BizException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author liyc
 */
@Service
@RequiredArgsConstructor
public class OperateLogAppServiceImpl implements OperateLogAppService {

	private final OperateLogRepository operateLogRepository;

	@Override
	public Response pageBy(OperateLogQueryDTO operateLogQuery, Pageable pageable) {
		Page<OperateLog> operateLogPage = operateLogRepository.findAll((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new LinkedList<>();

			if (StringUtils.hasLength(operateLogQuery.getUsername())) {
				predicates.add(
						criteriaBuilder.like(root.get("operateUserName"), operateLogQuery.getUsername() + "%"));
			}

			if (StringUtils.hasLength(operateLogQuery.getStartDate()) && StringUtils.hasLength(operateLogQuery.getEndDate())) {
				Date startDate = DateUtil.parseDate(operateLogQuery.getStartDate());
				Date endDate = DateUtil.parseDate(operateLogQuery.getEndDate());
				predicates.add(criteriaBuilder.between(root.get("createTime"), startDate, endDate));
			}

			if (operateLogQuery.getSuccessFlag() != null) {
				predicates.add(
						criteriaBuilder.equal(root.get("successFlag"), operateLogQuery.getSuccessFlag()));
			}

			if (predicates.size() > 0) {
				query.where(predicates.toArray(new Predicate[0]));
			}
			return query.getRestriction();
		}, pageable);
		return SingleResponse.ok(operateLogPage);
	}

	@Override
	public Response detail(Long operateLogId) {
		OperateLog operateLog = operateLogRepository.findById(operateLogId).orElseThrow(() -> new BizException("非法参数，查询不到id:" + operateLogId));
		return SingleResponse.ok(operateLog);
	}
}
