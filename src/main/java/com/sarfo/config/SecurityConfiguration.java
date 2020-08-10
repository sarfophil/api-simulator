package com.sarfo.config;

import com.sarfo.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthService authService;
    private final AppProperties properties;
    public SecurityConfiguration(AuthService authService,AppProperties properties){
        this.authService = authService;
        this.properties = properties;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/v1/simulate/**").permitAll()
                .antMatchers("/v1/account").permitAll()
                .antMatchers("/v1/**").authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(),properties))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),properties))
                // this disables session creation on spring security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
