package net.koodar.forge.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public class SpringEventInitializer implements InitializingBean {

	private final ApplicationContext context;

	@Override
	public void afterPropertiesSet() throws Exception {
		DomainEventContext.getInstance().initContext(context);
	}
}
