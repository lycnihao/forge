package net.koodar.forge.common.event;

import lombok.extern.slf4j.Slf4j;
import net.koodar.forge.common.handler.Handler;
import net.koodar.forge.common.handler.IHandler;

/**
 * @author liyc
 */
@Slf4j
@Handler
public class ExampleEventHandler implements IHandler<ExampleEvent> {

	@Override
	public void handler(ExampleEvent event) {
		log.info("name :{},detail :{}", event.getUsername(), event.getDetail());
	}

}
