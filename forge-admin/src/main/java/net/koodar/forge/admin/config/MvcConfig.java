package net.koodar.forge.admin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Mvc configuration.
 *
 * @author liyc
 */
@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

	private final PageableHandlerMethodArgumentResolver pageableResolver;
	private final SortHandlerMethodArgumentResolver sortResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		// 设置是否从1开始的页码索引
		pageableResolver.setOneIndexedParameters(true);
		resolvers.add(pageableResolver);
		resolvers.add(sortResolver);
	}

}
