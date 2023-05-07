package net.koodar.forge.common.event;

import net.koodar.forge.common.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author liyc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class EventTest {

	@Test
	public void testEvent() {
		EventPusher.push(new ExampleEvent("user", "用户"));
		System.out.println("push event success");
	}

}
