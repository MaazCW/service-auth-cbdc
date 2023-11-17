//package com.opl.cbdc.common.service.auth.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//
//import com.opl.cbdc.common.api.auth.utils.AuthCredentialUtils;
//import com.opl.cbdc.common.service.auth.security.CustomAuthenticationEntryPoint;
//
//@Configuration
//public class OAuth2Configuration {
//
//    @Configuration
//    @EnableResourceServer
//    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//        @Autowired
//        private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//        	http
//                    .exceptionHandling()
//                    .authenticationEntryPoint(customAuthenticationEntryPoint)
//                    .and()
//                    //required csrf protection for authorize 
//                    .csrf()
////                    .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
//                    .disable()
//                    .headers()
//                    .frameOptions().disable().and()
//                    //no need to create session
//                    .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
//                    .authorizeRequests()
//                    //this url allowed without authentication
//                    .antMatchers("/capitaworld/**","/capitaworld/getRefreshToken").permitAll()
//                    //this url not allowed without authentication
//                    .antMatchers("/accessTokenValidOrNot").authenticated();
//
//        }
//
//    }
//
//    @Configuration
//    @EnableAuthorizationServer
//    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter implements EnvironmentAware {
//
//        private static final String PROP_TOKEN_VALIDITY_SECONDS = "authentication.oauth.tokenValidityInSeconds";
//        private static final String PROP_TOKEN_VALIDITY_SECONDS_MOBILE = "authentication.oauth.tokenValidityInSeconds.mobile";
//
//       
//        @Autowired
//        private Environment environment; 
//        
//        
//        @Autowired
//        private DataSource dataSource;
//
//        @Bean
//        public TokenStore tokenStore() {
//        	//store refresh_token and access_token in database
//            return new JdbcTokenStore(dataSource);
//        }
//
//        // AuthenticationManager is autowired there and seems to initialize with default DAOAuthenticationProvider, 
//        //and for some reason it doesn't use custom JPA 
//        @Autowired
//        @Qualifier("authenticationManagerBean")
//        private AuthenticationManager authenticationManager;
//
//        @Override
//        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
//                throws Exception {
//        	endpoints
//                    .tokenStore(tokenStore())
//                    .authenticationManager(authenticationManager);
//        }
//
//        @Override
//        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        	clients
//                    .inMemory()
//                    .withClient(AuthCredentialUtils.CLIENT_ID)
//                    .scopes("read", "write")//this scope for user 
////                    .authorities(Authorities.ROLE_ADMIN.name(), Authorities.ROLE_USER.name())
//                    .authorizedGrantTypes("password", "refresh_token")//this is grant type u can change grant type
//                    .secret(AuthCredentialUtils.CLIENT_SECRET)//set secret credential which available on application.properties
//                    //for set token validity
//                    .accessTokenValiditySeconds(environment.getProperty(PROP_TOKEN_VALIDITY_SECONDS, Integer.class, 1800));
////                    .and()
////                    .inMemory()
////                    .withClient(AuthCredentialUtils.MOBILE_CLIENT_ID)
////                    .scopes("read", "write")//this scope for user 
//////                    .authorities(Authorities.ROLE_ADMIN.name(), Authorities.ROLE_USER.name())
////                    .authorizedGrantTypes("password", "refresh_token")//this is grant type u can change grant type
////                    .secret(AuthCredentialUtils.MOBILE_CLIENT_SECRET)//set secret credential which available on application.properties
////                    //for set token validity
////                    .accessTokenValiditySeconds(environment.getProperty(PROP_TOKEN_VALIDITY_SECONDS_MOBILE, Integer.class, 1800));
//        }
//
//        @Override
//        public void setEnvironment(Environment environment) {
//        	this.environment = environment;
//            /*this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);*/
//        }
//        
//        @Override 
//        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception { 
//            oauthServer.checkTokenAccess("permitAll()"); 
//        }
//
//    }
//
//}