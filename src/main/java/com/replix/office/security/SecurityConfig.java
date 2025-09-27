package com.replix.office.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    //=tokenBased
    @Value("${replix.auth.filter.userdetails:null}")
    private String authFilter;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Bean
    public OncePerRequestFilter authenticationJwtTokenFilter() {
        System.out.println("-----------------authFilter--------------- " +authFilter);//TODO:log
        if(authFilter.equals("tokenBased")){
            System.out.println("----------------created tokenBased user details filter--------------- ");//TODO:log
            return new AuthTokenFilterJwtUserDetails();
        } else if (authFilter.equals("daoBased")) {
            System.out.println("----------------created daoBased user details filter--------------- ");//TODO:log
            return new AuthTokenFilterDaoUserDetails();
        }
        else{
            System.out.println("----------------created daoBased user details filter as default filter--------------- ");//TODO:log
            return new AuthTokenFilterDaoUserDetails();
        }
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->{
                            auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                        auth.requestMatchers("/api/test/**").permitAll();
                            auth.requestMatchers("/error").permitAll();
                            /*When the validation fails (e.g. @Valid constraint violation), Spring MVC forwards to /error.
                            But /error itself is protected by Spring Security, and since the user is anonymous, it denies access to /error.*/
        auth.anyRequest().authenticated();}
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
