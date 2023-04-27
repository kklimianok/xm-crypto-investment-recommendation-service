package com.xm.services.investment.crypto.rs.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * REST application class.
 *
 * @author Kanstantsin_Klimianok
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@ImportResource("classpath:cirs-rest-api.xml")
public class CryptoInvestmentRecommendation {

    public static void main(String[] args) {
        SpringApplication.run(CryptoInvestmentRecommendation.class, args);
    }
}
