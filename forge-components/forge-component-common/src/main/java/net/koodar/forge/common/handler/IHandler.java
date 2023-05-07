package net.koodar.forge.common.handler;

import net.koodar.forge.common.event.IEvent;

/**
 * handler 订阅
 * @author liyc
 */
public interface IHandler<T extends IEvent> {

	/**
	 * 订阅触发
	 *
	 * @param event 触发event
	 */
	void handler(T event);

	/**
	 * 异常回掉,在多订阅的情况下,为了实现订阅的独立性,将异常的处理放在回掉函数中。
	 *
	 * @param exception 异常信息
	 */
	default void error(Exception exception) {
	};

}
