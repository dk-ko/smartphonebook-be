package com.soda.phonebook.security;

import javax.servlet.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final Filter ssoFilter;

    public SecurityConfig(Filter ssoFilter) {
        this.ssoFilter = ssoFilter;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    		http.antMatcher("/**")
    		
    		.authorizeRequests()
    			.antMatchers("/", "/h2/**", "/favicon.ico", "/swagger-ui.html**/**", "/api/login**")
        			.permitAll()
        		.antMatchers(HttpMethod.OPTIONS, "/**")
        			.permitAll()
        		.anyRequest()
                	.authenticated()
        .and().sessionManagement()
        		.sessionCreationPolicy(SessionCreationPolicy.NEVER)
        
        .and().logout()
        		.logoutRequestMatcher(new AntPathRequestMatcher("/api/logout_processing"))
        		.logoutSuccessUrl("/")
        			.permitAll()
        		.invalidateHttpSession(true)
        		
        	.and().headers()
        		.frameOptions()
//        		.sameOrigin()
        
        	.and().csrf().disable()
        		
        	.addFilterBefore(ssoFilter, BasicAuthenticationFilter.class);
    }
}