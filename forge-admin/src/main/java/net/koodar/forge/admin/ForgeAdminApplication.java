package net.koodar.forge.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan("net.koodar.forge")
@EnableAspectJAutoProxy
@SpringBootApplication
public class ForgeAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForgeAdminApplication.class, args);
	}

}
