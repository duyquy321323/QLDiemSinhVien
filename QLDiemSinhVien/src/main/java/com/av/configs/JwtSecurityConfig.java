/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.av.configs;

import com.av.filters.CustomAccessDeniedHandler;
import com.av.filters.JwtAuthenticationKeycloakConverter;
import com.av.filters.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Admin
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.av.controllers",
    "com.av.repository",
    "com.av.service",
    "com.av.components"})
@Order(1)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${keycloak.client-id}")
    private String realm;
//    @Bean
//    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
//        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
//        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
//        return jwtAuthenticationTokenFilter;
//    }



//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable crsf cho đường dẫn /rest/**
        http.csrf().disable()
                .authorizeRequests().antMatchers("/api/login/").permitAll()
                .antMatchers("/api/users/").permitAll()
                .antMatchers("/api/send-code/").permitAll();
        http.antMatcher("/api/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").access("hasRole('ROLE_GV') or hasRole('ROLE_SV')")
                .antMatchers(HttpMethod.POST, "/api/**").access("hasRole('ROLE_GV') or hasRole('ROLE_SV')")
                .antMatchers(HttpMethod.DELETE, "/api/**").access("hasRole('ROLE_GV') or hasRole('ROLE_SV')").and()
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtAuthenticationKeycloakConverter(realm)))
                )
//                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}
