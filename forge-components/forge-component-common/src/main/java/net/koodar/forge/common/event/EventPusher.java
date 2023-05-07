package net.koodar.forge.common.event;

/**
 * Even推送助手
 *
 * @author liyc
 */
public class EventPusher {

	public static void push(IEvent event) {
		DomainEventContext.getInstance().push(event);
	}

}
