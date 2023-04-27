package com.xm.services.investment.crypto.rs.dataloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.boot.WebApplicationType.NONE;

/**
 * Basic data loader application class.
 *
 * @author Kanstantsin_Klimianok
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
@ImportResource("classpath:cirs-dataloader.xml")
public class DataLoaderApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(DataLoaderApplication.class);
        application.setWebApplicationType(NONE);
        application.run(args);
    }

}
