package net.koodar.forge.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("net.koodar.forge")
@ConfigurationPropertiesScan("net.koodar.forge")
@EntityScan("net.koodar.forge")
@EnableJpaRepositories("net.koodar.forge")
@EnableAspectJAutoProxy
@SpringBootApplication
public class ForgeAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForgeAdminApplication.class, args);
	}

}
