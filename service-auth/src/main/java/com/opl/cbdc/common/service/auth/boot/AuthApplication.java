package com.opl.cbdc.common.service.auth.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@ComponentScan(basePackages = { "com.opl" })
@EnableAutoConfiguration
@EnableAsync
@EnableAuthorizationServer
public class AuthApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AuthApplication.class, args);
	}

}
