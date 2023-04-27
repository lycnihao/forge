package net.koodar.forge.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("net.koodar.forge")
@SpringBootApplication
public class ForgeAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForgeAdminApplication.class, args);
	}

}
