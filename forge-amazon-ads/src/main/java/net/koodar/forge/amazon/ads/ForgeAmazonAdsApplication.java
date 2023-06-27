package net.koodar.forge.amazon.ads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("net.koodar.forge")
@ConfigurationPropertiesScan("net.koodar.forge")
@SpringBootApplication
public class ForgeAmazonAdsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForgeAmazonAdsApplication.class, args);
    }

}
