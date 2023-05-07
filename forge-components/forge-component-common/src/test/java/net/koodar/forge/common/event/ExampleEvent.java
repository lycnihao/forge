package net.koodar.forge.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liyc
 */
@Getter
@Setter
@AllArgsConstructor
public class ExampleEvent implements IEvent {

	private String username;

	private String detail;

}
