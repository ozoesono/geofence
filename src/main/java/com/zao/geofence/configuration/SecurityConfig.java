package com.zao.geofence.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * NoConfiguration setup here so that the ApiExceptionHandler does return a 401 for all requests.
 */
@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        log.info("Disabling security");
        web.ignoring().antMatchers("/**");
    }
}
