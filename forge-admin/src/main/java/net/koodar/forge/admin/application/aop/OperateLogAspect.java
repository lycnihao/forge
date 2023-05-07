package net.koodar.forge.admin.application.aop;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.koodar.forge.common.event.EventPusher;
import net.koodar.forge.common.module.operatelog.OperateLogEvent;
import net.koodar.forge.common.utils.ExceptionUtils;
import net.koodar.forge.common.utils.JsonUtils;
import net.koodar.forge.security.userdetails.AppUserDetails;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * 操作日志记录处理
 *
 * @author liyc
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

	@AfterReturning(pointcut = "@within(operateLog)", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, net.koodar.forge.common.module.operatelog.OperateLog operateLog, Object jsonResult) {
		handleLog(joinPoint, null);
	}

	@AfterThrowing(value = "@within(operateLog)", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, net.koodar.forge.common.module.operatelog.OperateLog operateLog, Exception e) {
		handleLog(joinPoint, e);
	}

	protected void handleLog(final JoinPoint joinPoint, final Exception e) {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

			// 用户
			AppUserDetails user = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// 参数
			Object[] args = joinPoint.getArgs();
			String params = JsonUtils.objectToJson(args);
			// 方法名称
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String operateMethod = className + "." + methodName;
			// 失败信息
			String failReason = null;
			boolean successFlag = true;
			if (e != null) {
				successFlag = false;
				failReason = ExceptionUtils.getStackTrace(e);
			}

			OperateLogEvent operateLogEvent =
					OperateLogEvent.builder()
							.operateUserId(user.getUserId())
							.operateUserName(user.getUsername())
							.url(request.getRequestURI())
							.method(operateMethod)
							.param(params)
							.ip(user.getIp())
							.userAgent(user.getUserAgent())
							.failReason(failReason)
							.successFlag(successFlag).build();

			Operation operation = this.getOperation(joinPoint);
			if (operation != null) {
				operateLogEvent.setContent(operation.summary());
			}
			Tag api = this.getTag(joinPoint);
			if (api != null) {
				operateLogEvent.setModule(api.name());
			}
			EventPusher.push(operateLogEvent);
		} catch (Exception exp) {
			log.error("保存操作日志异常:{}", exp.getMessage());
			exp.printStackTrace();
		}
	}

	/**
	 * swagger Operation
	 *
	 * @param joinPoint 连接点
	 * @return 注解
	 */
	private Operation getOperation(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(Operation.class);
		}
		return null;
	}

	private Tag getTag(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		return AnnotationUtils.findAnnotation(method.getDeclaringClass(), Tag.class);
	}

}
