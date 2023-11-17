package com.opl.cbdc.common.service.auth.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.opl.cbdc.common.service.auth.config.PasswordEncoderUtils;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	@Autowired
	UserDetailsService userDetailsService;

	@Bean
	protected AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoderUtils();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/actuator/**",
				"/getRefreshToken",
				"/getAccessToken",
				"/checkAccessToken",
				"/logoutUser",
				"/getTokenByLoginToken",
				"/getTokenByLoginTokenForSidbi",
				"/getByLoginToken",
				"/getLastLoginDetailsFromUserId",
				"/getTokensByUserId",
				"/check_user_loggined",
				"/checkAuthStarted",
				"/updateCustomerInTokenMapping",
				"/getTokenForBankerAsPartner"
				);
	}
	
}
